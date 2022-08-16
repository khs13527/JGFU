package com.sparta.backend.service;

import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.repository.DibsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DibsService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PostService postService;
    private final DibsRepository dibsRepository;
    @Transactional
    public ResponseDto<?> dibsUpDown(Long postId, HttpServletRequest request){
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if(dibsRepository.findByPostAndMember(post,member).isEmpty()){
            Dibs dibs = Dibs.builder()
                    .member(member)
                    .post(post)
                    .build();
            dibsRepository.save(dibs);
            return ResponseDto.success(true);
        }else{
            dibsRepository.deleteByPostAndMember(post, member);
         return ResponseDto.success(false);
        }
    }
    @org.springframework.transaction.annotation.Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return jwtTokenProvider.getMemberFromAuthentication();
    }
}

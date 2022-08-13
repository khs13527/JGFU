package com.sparta.backend.service;


import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.MyPostResponseDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.dto.response.TwoSetsResponseDto;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.repository.CommentRepository;
import com.sparta.backend.repository.DibsRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final DibsRepository dibsRepository;
    private final CommentRepository commentRepository;


    public TwoSetsResponseDto<?> searchMyPage(HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) {
            return TwoSetsResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return TwoSetsResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }


        List<Post> postList = postRepository.findAllByMember(member);
        List<MyPostResponseDto> myPostResponseDtoList = new ArrayList<>();

        List<Dibs> dibsPostList = dibsRepository.findAllByMember(member);
        List<MyPostResponseDto> dibsMyPostResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            Long dibCount = dibsRepository.countByPost(post);
            Long commentsCount = commentRepository.countByPost(post);
            myPostResponseDtoList.add(
                    MyPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .price(post.getPrice())
                            .memberId(member.getId())
                            .imgUrl(post.getImgUrl())
                            .category(post.getCategory())
                            .dibCount(dibCount)
                            .commentsCount(commentsCount)
                            .view(post.getViews())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }



        for (Dibs dibs : dibsPostList) {
            Post dibsPost = postRepository.findByDibsSet(dibs);
            Long dibCount = dibsRepository.countByPost(dibsPost);
            Long commentsCount = commentRepository.countByPost(dibsPost);
            dibsMyPostResponseDtoList.add(
                    MyPostResponseDto.builder()
                            .id(dibsPost.getId())
                            .title(dibsPost.getTitle())
                            .content(dibsPost.getContent())
                            .price(dibsPost.getPrice())
                            .memberId(member.getId())
                            .imgUrl(dibsPost.getImgUrl())
                            .category(dibsPost.getCategory())
                            .dibCount(dibCount)
                            .commentsCount(commentsCount)
                            .view(dibsPost.getViews())
                            .createdAt(dibsPost.getCreatedAt())
                            .modifiedAt(dibsPost.getModifiedAt())
                            .build()
            );
        }
        return TwoSetsResponseDto.success(myPostResponseDtoList,dibsMyPostResponseDtoList);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return jwtTokenProvider.getMemberFromAuthentication();
    }
}

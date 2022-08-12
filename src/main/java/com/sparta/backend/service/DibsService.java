package com.sparta.backend.service;

import com.sparta.backend.domain.Dibs;
import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.repository.DibsRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DibsService {
    private final MemberRepository memberRepository;
    private final DibsRepository dibsRepository;
    private final PostRepository postRepository;
    @Transactional
    public ResponseDto<?> dibsUpDown(Long postId, Long userId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(dibsRepository.findByPost(post).equals(null)){
            Dibs dibs = new Dibs();
            dibs.setMember(member);
            dibs.setPost(post);
            return ResponseDto.success("up");
        }else{
            dibsRepository.deleteByPostAndMember(post, member);
         return ResponseDto.success("down");
        }
    }
}

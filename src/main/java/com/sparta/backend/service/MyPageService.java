package com.sparta.backend.service;

import com.sparta.backend.domain.Member;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.MyPostResponseDto;
import com.sparta.backend.dto.response.TwoSetsResponseDto;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    public TwoSetsResponseDto<?> searchMyPage(Long userId){
        Member member = memberRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        List<Post> postList = postRepository.findAllByMember(member);
        List<MyPostResponseDto> myPostResponseDtoList = new ArrayList<>();

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
                            .dibCount()
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }

        return TwoSetsResponseDto.success("a","b");
    }
}

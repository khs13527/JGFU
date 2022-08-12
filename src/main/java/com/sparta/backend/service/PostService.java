package com.sparta.backend.service;

import com.sparta.backend.domain.Comment;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.AllPostResponseDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.repository.CommentRepository;
import com.sparta.backend.repository.DibsRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final DibsRepository dibsRepository;
    public ResponseDto<?> searchPosts(){
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();


        for (Post post : postList) {
            Long dibCount = dibsRepository.countByPost(post);
            Long commentsCount = commentRepository.countByPost(post);
            allPostResponseDtoList.add(
                    AllPostResponseDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .imgUrl(post.getImgUrl())
                            .price(post.getPrice())
                            .dibCount(dibCount)
                            .view(post.getViews())
                            .commentsCount(commentsCount)
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(allPostResponseDtoList);
    }
}

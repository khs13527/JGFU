package com.sparta.backend.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.sparta.backend.S3.S3Uploader;
import com.sparta.backend.domain.Comment;
import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.request.PostRequestDto;
import com.sparta.backend.dto.response.CommentResponseDto;
import com.sparta.backend.dto.response.PostResponseDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.repository.CommentRepository;
import com.sparta.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<?> createPost(PostRequestDto postRequestDto, MultipartFile file, HttpServletRequest request) throws IOException {
        // 로그인 검증
        String imgUrl = s3Uploader.uploadFiles(file, "static");

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .price(postRequestDto.getPrice())
                .imgUrl(imgUrl)
                .category(postRequestDto.getCategory())
                .views(0)
                .member(member)
                .build();
        postRepository.save(post);
        return ResponseDto.success("게시글 게시 완료");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPostDetail(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
        List<Comment> commentList = commentRepository.findAllByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .memberId(comment.getMember().getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );

        }
        return ResponseDto.success(
                PostResponseDto.builder()
                        .id(post.get().getId())
                        .title(post.get().getTitle())
                        .content(post.get().getContent())
                        .price(post.get().getPrice())
                        .imgUrl(post.get().getImgUrl())
                        .category(post.get().getCategory())
                        .views(0L)
                        .build()
        );

    }
}

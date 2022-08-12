package com.sparta.backend.service;

import com.amazonaws.Response;
import com.sparta.backend.domain.Comment;
import com.sparta.backend.dto.request.CommentRequestDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long postId, HttpServletRequest request){
        // 로그인 검증

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(commentRequestDto.getContent())
                .build();
        commentRepository.save(comment);
        return ResponseDto.success("댓글 게시 완료");
    }

    @Transactional
    public ResponseDto<?> updateComment(CommentRequestDto commentRequestDto, Long postId, Long commentId, HttpServletRequest request){
        // 로그인 검증

        comment.update(commentRequestDto);
        return ResponseDto.success("댓글 수정 완료");
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long postId, Long commentId, HttpServletRequest request){
        //로그인 검증

        commentRepository.delete(comment);
    }
}

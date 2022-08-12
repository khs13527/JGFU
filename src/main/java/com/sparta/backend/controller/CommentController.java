package com.sparta.backend.controller;

import com.sparta.backend.dto.request.CommentRequestDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping(value = "/api/auth/posts/{postId}/comments")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postId, HttpServletRequest request) {
        return commentService.createComment(commentRequestDto, postId, request);
    }
    @PutMapping(value = "/api/auth/posts/{postId}/comments/{commentId}")
    public ResponseDto<?> updateComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        return commentService.updateComment(commentRequestDto, postId, commentId);
    }

    @DeleteMapping(value = "/api/auth/posts/{postId}/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,HttpServletRequest request){
        return commentService.deleteComment(postId, commentId, request);
    }
}

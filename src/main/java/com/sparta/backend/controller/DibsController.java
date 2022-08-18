package com.sparta.backend.controller;

import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.service.DibsService;
import com.sparta.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DibsController {
    private final DibsService dibsService;
    private final PostService postService;
    @PostMapping("/api/auth/dibs/posts/{postId}")
    public ResponseDto<?> toggleDibs(@PathVariable Long postId, HttpServletRequest request) {
        return dibsService.toggleDibs(postId, request);
    }
    @GetMapping("/api/auth/dibs/posts/{postId}")
    public ResponseDto<?> memberGetPostDetail(@PathVariable Long postId, HttpServletRequest request){
        return postService.getMemberPostDetail(postId, request);
    }
}

package com.sparta.backend.controller;

import com.sparta.backend.dto.request.PostRequestDto;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/api/auth/posts")
    public ResponseDto<?> createPost(@RequestPart("data")PostRequestDto postRequestDto, @RequestPart("image")MultipartFile file, HttpServletRequest request) throws IOException {
        return postService.createPost(postRequestDto, file, request);
    }
    @GetMapping(value = "/api/posts/{postId}")
    public ResponseDto<?> getPostDetail(@PathVariable Long postId){
        return postService.getPostDetail(postId);
    }
}

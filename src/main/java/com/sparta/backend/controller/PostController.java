package com.sparta.backend.controller;

import com.sparta.backend.domain.Post;
import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/api/posts")
    public ResponseDto<?> postsSearch(){
        return postService.searchPosts();
    }
}

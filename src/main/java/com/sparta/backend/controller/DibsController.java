package com.sparta.backend.controller;

import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.service.DibsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DibsController {
    private final DibsService dibsService;
    @PostMapping("/api/auth/dibs/posts/{postId}/user/{userId}")
    public ResponseDto<?> dibsButton(@PathVariable Long postId, @PathVariable Long userId) {
        return ResponseDto.success(dibsService.dibsUpDown(postId, userId));
    }
}

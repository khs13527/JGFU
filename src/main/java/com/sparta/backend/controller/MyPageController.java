package com.sparta.backend.controller;

import com.sparta.backend.dto.response.ResponseDto;
import com.sparta.backend.dto.response.TwoSetsResponseDto;
import com.sparta.backend.service.MyPageService;
import com.sparta.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    @GetMapping("/api/auth/my-page")
    public TwoSetsResponseDto<?> myPageSearch(HttpServletRequest request){
        return myPageService.searchMyPage(request);
    }
}

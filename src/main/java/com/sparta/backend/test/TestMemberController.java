//package com.sparta.backend.test;
//
//import com.sparta.backend.dto.response.ResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class TestMemberController {
//    private final TestMemberService testMemberService;
//
//    //임시 회원가입 기능
//    @PostMapping("/api/member/test")
//    public ResponseDto<?> userCreate(@RequestBody TestMemberCreateDto requestDto){
//        return ResponseDto.success(testMemberService.createMember(requestDto));
//    }
//}

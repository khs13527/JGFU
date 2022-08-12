//package com.sparta.backend.test;
//
//import com.sparta.backend.domain.Member;
//import com.sparta.backend.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
////임시 회원가입 서비스
//@Service
//@RequiredArgsConstructor
//public class TestMemberService {
//    private final MemberRepository memberRepository;
//
//    @Transactional
//    public Member createMember(TestMemberCreateDto requestDto){
//        Member member =new Member(requestDto);
//        memberRepository.save(member);
//        return member;
//    }
//}

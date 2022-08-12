package com.sparta.backend.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestMemberCreateDto {
    private String memberId;
    private String password;
}
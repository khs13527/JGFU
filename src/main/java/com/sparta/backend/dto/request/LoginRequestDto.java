package com.sparta.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @javax.validation.constraints.NotBlank
    private String memberId;

    @NotBlank
    private String password;

}

package com.sparta.backend.exception;

import com.sparta.backend.dto.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
@Slf4j
@RestControllerAdvice
public class exceptionHandler {
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseDto<?> handleBadRequestException(){
        return ResponseDto.fail("BAD_REQUEST", "이미지 파일이 없습니다.");
    }
}

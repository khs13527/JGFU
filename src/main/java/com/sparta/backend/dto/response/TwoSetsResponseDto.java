package com.sparta.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TwoSetsResponseDto<T> {
    private boolean success;
    private T data1;
    private T data2;
    private Error error;

    public static <T> TwoSetsResponseDto<T> success(T data1 ,T data2) {
        return new TwoSetsResponseDto<>(true, data1, data2, null);
    }

    public static <T> TwoSetsResponseDto<T> fail(String code, String message) {
        return new TwoSetsResponseDto<>(false, null, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    static class Error {
        private String code;
        private String message;
    }

}
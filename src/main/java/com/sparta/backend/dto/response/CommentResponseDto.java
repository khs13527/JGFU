package com.sparta.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

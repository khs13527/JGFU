package com.sparta.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String memberId;
    private Boolean isEditMode;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long postId;
}

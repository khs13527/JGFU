package com.sparta.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private String memberId;
    private String imgUrl;
    private String category;
    private Long dibCount;
    private Long commentsCount;
    private Long view;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

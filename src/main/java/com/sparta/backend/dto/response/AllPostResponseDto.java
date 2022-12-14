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
public class AllPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private Long price;
    private Boolean isDone;
    private String category;
    private Long dibCount;
    private Long view;
    private Long commentsCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

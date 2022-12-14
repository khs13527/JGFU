package com.sparta.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String memberId;
    private Long price;
    private String imgUrl;
    private String category;
    private Long dibCount;
    private Long views;
    private Boolean isEditMode;
    private Boolean isDone;
    private List<CommentResponseDto> commentResponseDtoList;
}

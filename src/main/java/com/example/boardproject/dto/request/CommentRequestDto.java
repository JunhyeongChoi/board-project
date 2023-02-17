package com.example.boardproject.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentRequestDto {

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;

}

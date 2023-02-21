package com.example.boardproject.dto.request;

import com.example.boardproject.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    // 수정 컨트롤러에서 사용
    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }

    public static CommentRequestDto toDto(Comment comment) {
        return CommentRequestDto.builder()
                .content(comment.getContent())
                .build();
    }

}

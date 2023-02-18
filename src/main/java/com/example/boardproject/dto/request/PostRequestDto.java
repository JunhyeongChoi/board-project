package com.example.boardproject.dto.request;

import com.example.boardproject.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class PostRequestDto {

    @NotEmpty(message="제목은 필수 항목입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message="내용은 필수 항목입니다.")
    private String content;

    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
    }

}

package com.example.boardproject.dto;

import com.example.boardproject.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

    @Builder
    public PostDto(Long id, String title, String content, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .build();
    }

}

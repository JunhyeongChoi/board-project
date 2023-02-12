package com.example.boardproject.dto;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public PostDto(Long id, String title, String content, LocalDateTime createDate, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.comments = comments;
    }

    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .comments(post.getComments())
                .build();
    }

}

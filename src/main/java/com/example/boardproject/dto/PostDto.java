package com.example.boardproject.dto;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
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
    private Member member;

    @Builder
    public PostDto(Long id,
                   String title,
                   String content,
                   LocalDateTime createDate,
                   List<Comment> comments,
                   Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.comments = comments;
        this.member = member;
    }

    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .comments(post.getComments())
                .member(post.getMember())
                .build();
    }

}

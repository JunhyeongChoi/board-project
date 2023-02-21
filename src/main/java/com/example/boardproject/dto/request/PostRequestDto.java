package com.example.boardproject.dto.request;

import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class PostRequestDto {

    @NotBlank(message="제목은 필수 항목입니다.")
    @Size(max=200)
    private String title;

    @NotBlank(message="내용은 필수 항목입니다.")
    private String content;

    private Member member;

    // 수정 컨트롤러에서 사용
    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostRequestDto toDto(Post post) {
        return PostRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .createDate(LocalDateTime.now())
                .build();
    }

}

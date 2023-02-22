package com.example.boardproject.domain;

import com.example.boardproject.domain.user.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createDate;

    @Setter
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    public Reply(String content, LocalDateTime createDate, Member member, Comment comment) {
        this.content = content;
        this.createDate = createDate;
        this.member = member;
        this.comment = comment;
    }

}

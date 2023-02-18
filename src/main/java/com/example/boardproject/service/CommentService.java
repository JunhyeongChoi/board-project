package com.example.boardproject.service;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import com.example.boardproject.dto.PostDto;
import com.example.boardproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(Post post, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment);
    }

}

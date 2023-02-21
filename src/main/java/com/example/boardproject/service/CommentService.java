package com.example.boardproject.service;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment saveComment(Post post, Member member, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .post(post)
                .member(member)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public Comment getComment(Long postId) {
        return commentRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
    }

    // 댓글 수정
    public void updateComment(Comment comment, CommentRequestDto commentRequestDto) {
        if (comment.getContent() == null)
            return;

        comment.setContent(commentRequestDto.getContent());
        comment.setModifyDate(LocalDateTime.now());
    }

    // 댓글 삭제
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}

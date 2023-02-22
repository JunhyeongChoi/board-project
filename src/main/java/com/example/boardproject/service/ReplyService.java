package com.example.boardproject.service;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Reply;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.dto.request.ReplyRequestDto;
import com.example.boardproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    // 대댓글 저장
    public Reply saveReply(Comment comment, Member member, String content) {
        Reply reply = Reply.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .comment(comment)
                .member(member)
                .build();

        Reply savedReply = replyRepository.save(reply);
        return savedReply;
    }

    // 대댓글 조회
    @Transactional(readOnly = true)
    public Reply getReply(Long postId) {
        return replyRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("대댓글을 찾을 수 없습니다."));
    }

    // 대댓글 수정
    public void updateReply(Reply reply, ReplyRequestDto replyRequestDto) {
        reply.setContent(replyRequestDto.getContent());
        reply.setModifyDate(LocalDateTime.now());
    }

    // 대댓글 삭제
    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }
    
}

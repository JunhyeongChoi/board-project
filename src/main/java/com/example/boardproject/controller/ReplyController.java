package com.example.boardproject.controller;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.Reply;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.dto.request.ReplyRequestDto;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
import com.example.boardproject.service.ReplyService;
import com.example.boardproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/reply")
@Controller
public class ReplyController {

    private final CommentService commentService;
    private final ReplyService replyService;
    private final UserService userService;

    // 대댓글 작성 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createReply(@PathVariable Long id,
                               @Valid ReplyRequestDto replyRequestDto,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal) {

        Comment comment = commentService.getComment(id);
        Post post = comment.getPost();
        Member member = userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);

            return "post";
        }

        Reply reply = replyService.saveReply(comment, member, replyRequestDto.getContent());
        return "redirect:/post/" + comment.getPost().getId() + "#reply-" + reply.getId();
    }

    // 대댓글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyReply(Model model,
                              @PathVariable Long id,
                              Principal principal) {

        Reply reply = replyService.getReply(id);
        // 수정페이지 들어갔을 때 내용이 그대로 채워져 있게끔 할 때 사용
        ReplyRequestDto replyRequestDto = ReplyRequestDto.toDto(reply);

        if(!principal.getName().equals(reply.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        model.addAttribute(replyRequestDto);
        return "reply_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String updateReply(@Valid ReplyRequestDto replyRequestDto,
                              BindingResult bindingResult,
                              Principal principal,
                              @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "reply_form";
        }

        Reply reply = replyService.getReply(id);
        if (!principal.getName().equals(reply.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        replyService.updateReply(reply, replyRequestDto);
        return "redirect:/post/" + reply.getComment().getPost().getId() + "#reply-" + id;
    }

    // 대댓글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteReply(Principal principal, @PathVariable Long id) {
        Reply reply = replyService.getReply(id);

        if (!principal.getName().equals(reply.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        replyService.deleteReply(reply);
        return "redirect:/post/" + reply.getComment().getPost().getId();
    }

}

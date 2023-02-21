package com.example.boardproject.controller;

import com.example.boardproject.domain.Comment;
import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
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
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    // 댓글 작성 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable Long id,
                               @Valid CommentRequestDto commentRequestDto,
                               BindingResult bindingResult,
                               Model model,
                               Principal principal) {

        Post post = postService.getPost(id);
        Member member = userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);

            return "post";
        }

        Comment comment = commentService.saveComment(post, member, commentRequestDto.getContent());
        return "redirect:/post/" + id + "#comment-" + comment.getId();
    }

    // 댓글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String CommentModify(Model model, @PathVariable Long id, Principal principal) {

        Comment comment = commentService.getComment(id);
        // 수정페이지 들어갔을 때 내용이 그대로 채워져 있게끔 할 때 사용
        CommentRequestDto commentRequestDto = CommentRequestDto.toDto(comment);

        if(!principal.getName().equals(comment.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        model.addAttribute(commentRequestDto);
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(@Valid CommentRequestDto commentRequestDto, BindingResult bindingResult,
                             Principal principal, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }

        Comment comment = commentService.getComment(id);
        if (!principal.getName().equals(comment.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        commentService.updateComment(comment, commentRequestDto);
        return "redirect:/post/" + comment.getPost().getId() + "#comment-" + id;
    }

    // 댓글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable Long id) {
        Comment comment = commentService.getComment(id);

        if (!principal.getName().equals(comment.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        commentService.deleteComment(comment);
        return "redirect:/post/" + comment.getPost().getId();
    }

}

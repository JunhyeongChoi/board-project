package com.example.boardproject.controller;

import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
import com.example.boardproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        commentService.saveComment(post, member, commentRequestDto.getContent());
        return "redirect:/post/" + id;
    }

}

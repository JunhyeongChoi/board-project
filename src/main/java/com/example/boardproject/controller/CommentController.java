package com.example.boardproject.controller;

import com.example.boardproject.domain.Post;
import com.example.boardproject.dto.request.CommentRequestDto;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable Long id,
                               @Valid CommentRequestDto commentRequestDto,
                               BindingResult bindingResult,
                               Model model) {
        Post post = postService.getPost(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "post";
        }

        commentService.saveComment(post, commentRequestDto.getContent());
        return "redirect:/post/" + id;
    }

}

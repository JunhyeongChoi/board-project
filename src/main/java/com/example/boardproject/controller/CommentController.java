package com.example.boardproject.controller;

import com.example.boardproject.domain.Post;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable Long id, @RequestParam String content) {
        Post post = postService.getPost(id);

        commentService.saveComment(post, content);

        return "redirect:/posts/" + id;
    }

}

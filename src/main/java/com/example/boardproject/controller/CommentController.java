package com.example.boardproject.controller;

import com.example.boardproject.domain.Post;
import com.example.boardproject.service.CommentService;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String createAnswer(@PathVariable Long id, @RequestParam String content, Model model) {
        // TODO: 답변 저장

        return "redirect:/posts/" + id;
    }

}

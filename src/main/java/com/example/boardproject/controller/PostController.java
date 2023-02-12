package com.example.boardproject.controller;

import com.example.boardproject.dto.PostDto;
import com.example.boardproject.dto.request.PostSaveRequest;
import com.example.boardproject.dto.request.PostUpdateRequest;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostController {

    private final PostService postService;

    // 게시판 조회
    @GetMapping
    String posts(Model model) {
        List<PostDto> posts = postService.getPosts();

        model.addAttribute("posts", posts);
        return "board";
    }

    // 게시글 조회
    @GetMapping("{postId}")
    String post(@PathVariable Long postId, Model model) {
        PostDto post = postService.getPostDto(postId);

        model.addAttribute("post", post);
        return "post";
    }

    // 게시글 작성
    @ResponseBody
    @PostMapping
    void create(@RequestBody PostSaveRequest postSaveRequest) {
        postService.savePost(postSaveRequest);
    }

    // 게시글 수정
    @ResponseBody
    @PutMapping("{postId}")
    void update(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(postId, postUpdateRequest);
    }

    // 게시글 삭제
    @ResponseBody
    @DeleteMapping("{postId}")
    void delete(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

}

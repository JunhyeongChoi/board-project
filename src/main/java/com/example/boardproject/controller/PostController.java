package com.example.boardproject.controller;

import com.example.boardproject.dto.PostDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.service.PagingService;
import com.example.boardproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostController {

    private final PostService postService;
    private final PagingService pagingService;

    // 게시판 조회
    @GetMapping
    String posts(Model model, @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostDto> posts = postService.getPosts(pageable);
        List<Integer> barNumbers = pagingService.getPagingNumbers(pageable.getPageNumber(), posts.getTotalPages());

        model.addAttribute("barNumbers", barNumbers);
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
    @GetMapping("/create")
    public String postCreate() {
        return "form";
    }

    @PostMapping("/create")
    public String postCreate(@Valid PostRequestDto postRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        postService.savePost(postRequestDto);
        return "redirect:/posts";
    }

    // 게시글 수정


    // 게시글 삭제


}

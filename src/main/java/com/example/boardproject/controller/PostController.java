package com.example.boardproject.controller;

import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.PostDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.service.PagingService;
import com.example.boardproject.service.PostService;
import com.example.boardproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostService postService;
    private final PagingService pagingService;
    private final UserService userService;

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

    // 게시글 작성 폼 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate() {
        return "form";
    }

    // 게시글 작성 (로그인 필요)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostRequestDto postRequestDto, BindingResult bindingResult, Principal principal) {
        Member member = userService.getMember(principal.getName());

        if (bindingResult.hasErrors()) {
            return "form";
        }

        postService.savePost(postRequestDto, member);
        return "redirect:/post";
    }

    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String postModify(Model model, @PathVariable Long id, Principal principal) {

        Post post = postService.getPost(id);
        // 수정페이지 들어갔을 때 내용이 그대로 채워져 있게끔 할 때 사용
        PostRequestDto postRequestDto = PostRequestDto.toDto(post);

        if(!principal.getName().equals(post.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        model.addAttribute(postRequestDto);

        return "form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String postModify(@Valid PostRequestDto postRequestDto, BindingResult bindingResult,
                                 Principal principal, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        Post post = postService.getPost(id);
        if (!principal.getName().equals(post.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }

        postService.updatePost(post, postRequestDto);
        return "redirect:/post/" + id;
    }

    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postDelete(Principal principal, @PathVariable Long id) {
        Post post = postService.getPost(id);
        
        if (!principal.getName().equals(post.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        postService.deletePost(post);
        return "redirect:/";
    }

}

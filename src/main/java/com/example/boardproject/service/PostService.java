package com.example.boardproject.service;

import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.PostDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::toDto);
    }

    @Transactional(readOnly = true)
    // id로 특정 게시글 조회
    public PostDto getPostDto(Long postId) {
        return postRepository.findById(postId)
                .map(PostDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

    // 게시글 저장
    public void savePost(PostRequestDto postRequestDto, Member member) {
        postRequestDto.setMember(member);

        postRepository.save(postRequestDto.toEntity());
    }

    // 게시글 수정
    public void updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        if (post.getTitle() != null) post.setTitle(postRequestDto.getTitle());
        if (post.getContent() != null) post.setContent(postRequestDto.getContent());
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }

}

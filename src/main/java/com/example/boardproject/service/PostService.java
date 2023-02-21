package com.example.boardproject.service;

import com.example.boardproject.domain.Post;
import com.example.boardproject.domain.SearchType;
import com.example.boardproject.domain.user.Member;
import com.example.boardproject.dto.PostDto;
import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

//    // 전체 게시글 조회
//    @Transactional(readOnly = true)
//    public Page<PostDto> getPosts(Pageable pageable) {
//        return postRepository.findAll(pageable).map(PostDto::toDto);
//    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return postRepository.findAll(pageable).map(PostDto::toDto);
        }

        if (searchType == SearchType.TITLE) {
            return postRepository.findByTitleContaining(searchKeyword, pageable).map(PostDto::toDto);
        } else if (searchType == SearchType.CONTENT) {
            return postRepository.findByContentContaining(searchKeyword, pageable).map(PostDto::toDto);
        } else if (searchType == SearchType.USERNAME) {
            return postRepository.findByMember_UsernameContaining(searchKeyword, pageable).map(PostDto::toDto);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
    public void updatePost(Post post, PostRequestDto postRequestDto) {
        if (post.getTitle() == null || post.getContent() == null)
            return;

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setModifyDate(LocalDateTime.now());
    }

    // 게시글 삭제
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

}

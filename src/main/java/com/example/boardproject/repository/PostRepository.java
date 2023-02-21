package com.example.boardproject.repository;

import com.example.boardproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContaining(String title, Pageable pageable);
    Page<Post> findByContentContaining(String content, Pageable pageable);
    Page<Post> findByMember_UsernameContaining(String userId, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

}

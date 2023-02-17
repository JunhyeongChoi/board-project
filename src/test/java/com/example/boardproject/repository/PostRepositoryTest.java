package com.example.boardproject.repository;

import com.example.boardproject.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void save() {
        Post post = Post.builder()
                .title("제목 테스트")
                .content("본문 테스트")
                .createDate(LocalDateTime.now())
                .build();
        this.postRepository.save(post);

        Post savedPost = postRepository.findById(1L).orElse(null);
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
    }

}

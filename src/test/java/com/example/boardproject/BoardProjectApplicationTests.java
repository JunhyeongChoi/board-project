package com.example.boardproject;

import com.example.boardproject.dto.request.PostRequestDto;
import com.example.boardproject.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private PostService postService;

	@Test
	void testJpa() {
		for (int i = 1; i <= 200; i++) {
			String title = "제목 테스트 " + i;
			String content = "내용 테스트";

			PostRequestDto postRequestDto = PostRequestDto.builder()
					.title(title)
					.content(content)
					.build();
			this.postService.savePost(postRequestDto);
		}
	}

}

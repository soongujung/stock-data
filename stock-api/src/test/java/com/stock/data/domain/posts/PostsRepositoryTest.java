package com.stock.data.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@After
	public void cleanup(){
		postsRepository.deleteAll();
	}

	@Test
	public void 게시글_저장후_불러오기(){
		String title = "테스트 제목 1";
		String content = "테스트 본문";
		String author = "helloSydney@gmail.com";

		postsRepository.save(
			Posts.builder()
				.content(content)
				.title(title)
				.author(author)
				.build()
		);

		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}

	@Test
	public void BaseTimeEntity_등록(){
		LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0,0, 0);
		postsRepository.save(
			Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build()
		);

		List<Posts> postsList = postsRepository.findAll();

		Posts post = postsList.get(0);

		System.out.println("====== createdDate :: "
			+ post.getCreatedDate()
			+ ", modifiedDate :: "
			+ post.getModifiedDate()
		);

		assertThat(post.getCreatedDate()).isAfter(now);
		assertThat(post.getModifiedDate()).isAfter(now);
	}
}

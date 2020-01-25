package com.stock.data.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

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

		// 롬복이 작동할 때는 아래 코드로...
//		postsRepository.save(
//			Posts.builder()
//				.content(content)
//				.title(title)
//				.author(author)
//				.build()
//		);

		// 롬복이 IDE에서 정상동작하지 않는다면...
		postsRepository.save(
			new Posts(title, content, author)
		);

		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);
//		assertThat(posts.getTitle()).isEqualTo(title);
//		assertThat(posts.getContent()).isEqualTo(content);
	}
}

package com.stock.data.web.posts;

import static org.assertj.core.api.Assertions.assertThat;

import com.stock.data.domain.posts.Posts;
import com.stock.data.domain.posts.PostsRepository;
import com.stock.data.web.posts.dto.PostsSaveRequestDto;
import com.stock.data.web.posts.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@After
	public void tearDown() throws Exception{
		postsRepository.deleteAll();
	}

	@Test
	public void Posts_등록된다() throws Exception{
		String title = "타이틀 ... ";
		String content = "content";

		PostsSaveRequestDto requestDto =
			PostsSaveRequestDto.builder()
				.title(title)
				.content(content)
				.author("어떤 작가님")
				.build();

		String url = "http://localhost:"
						+ port
						+ "/api/v1/posts";

		ResponseEntity<Long> responseEntity =
			restTemplate.postForEntity(url, requestDto, Long.class);

		assertThat(responseEntity.getStatusCode())
			.isEqualTo(HttpStatus.OK);

		assertThat(responseEntity.getBody())
			.isGreaterThan(0L);
	}

	@Test
	public void Posts_수정된다() throws Exception{
		Posts savedPosts = postsRepository.save(
			Posts.builder()
				.title("제목 1 ")
				.content("내용 1")
				.author("author")
				.build()
		);

		Long updateId = savedPosts.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";

		PostsUpdateRequestDto requestDto =
			PostsUpdateRequestDto.builder()
				.title(expectedTitle)
				.content(expectedContent)
				.build();

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

		ResponseEntity<Long> responseEntity =
			restTemplate.exchange(
				url, HttpMethod.PUT, requestEntity, Long.class
			);

		assertThat(responseEntity.getStatusCode())
				.isEqualTo(HttpStatus.OK);

		assertThat(responseEntity.getBody())
				.isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();

		assertThat(all.get(0).getTitle())
			.isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent())
			.isEqualTo(expectedContent);
	}
}

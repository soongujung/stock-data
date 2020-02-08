package com.stock.data.web.posts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.data.domain.posts.Posts;
import com.stock.data.domain.posts.PostsRepository;
import com.stock.data.web.posts.dto.PostsSaveRequestDto;
import com.stock.data.web.posts.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup(){
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@After
	public void tearDown() throws Exception{
		postsRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles = "USER")
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

		String strRequestDto = new ObjectMapper().writeValueAsString(requestDto);

		// when
		mvc.perform(
				post(url)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(strRequestDto)
			)
			.andExpect(status().isOk());

		// then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
	}

	@Test
	@WithMockUser(roles = "USER")
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

		String strRequestDto = new ObjectMapper().writeValueAsString(requestDto);

		mvc.perform(
				put(url)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(strRequestDto)
			)
			.andExpect(status().isOk());

		List<Posts> all = postsRepository.findAll();

		assertThat(all.get(0).getTitle())
			.isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent())
			.isEqualTo(expectedContent);
	}
}

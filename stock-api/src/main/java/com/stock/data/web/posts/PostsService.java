package com.stock.data.web.posts;

import com.stock.data.domain.posts.Posts;
import com.stock.data.domain.posts.PostsRepository;
import com.stock.data.web.posts.dto.PostsResponseDto;
import com.stock.data.web.posts.dto.PostsSaveRequestDto;
import com.stock.data.web.posts.dto.PostsUpdateRequestDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository
					.save(requestDto.toEntity())
					.getId();
	}

	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {

		Posts posts = postsRepository.findById(id)
			.orElseThrow(()->new IllegalArgumentException("사용자가 없습니다. ID = " + id));

		posts.update(requestDto.getTitle(), requestDto.getContent());
		return id;
	}

	public PostsResponseDto findById(Long id) {
		Posts post = postsRepository.findById(id)
			.orElseThrow(()->new IllegalArgumentException("사용자가 없습니다. ID = "+ id));
		return new PostsResponseDto(post);
	}
}

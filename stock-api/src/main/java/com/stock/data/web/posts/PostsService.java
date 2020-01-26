package com.stock.data.web.posts;

import com.stock.data.domain.posts.PostsRepository;
import com.stock.data.web.posts.dto.PostsSaveRequestDto;
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
}

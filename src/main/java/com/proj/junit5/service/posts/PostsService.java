package com.proj.junit5.service.posts;

import com.proj.junit5.domain.posts.Posts;
import com.proj.junit5.domain.posts.PostsRepository;
import com.proj.junit5.web.dto.PostsResponseDto;
import com.proj.junit5.web.dto.PostsSaveRequestDto;
import com.proj.junit5.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto dto) {

        return postsRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." + id));

        posts.update(dto.getTitle(), dto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다." + id));

        return new PostsResponseDto(entity);
    }
}

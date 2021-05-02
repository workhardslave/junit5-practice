package com.proj.junit5.web;

import com.proj.junit5.service.posts.PostsService;
import com.proj.junit5.web.dto.PostsResponseDto;
import com.proj.junit5.web.dto.PostsSaveRequestDto;
import com.proj.junit5.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping(value = "/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto dto) {

        return postsService.save(dto);
    }

    @GetMapping(value = "/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {

        return postsService.findById(id);
    }

    @PutMapping(value = "/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto dto) {

        return postsService.update(id, dto);
    }
}

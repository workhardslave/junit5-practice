package com.proj.junit5.domain.posts;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @DisplayName("1. 게시글을 등록하고 조회할 수 있다.")
    @Test
    public void test_1() throws Exception {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("황승환")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @DisplayName("2. 단일 조회를 할 수 있다.")
    @Test
    public void test_2() throws Exception {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("황승환")
                .build());

        // when
        Posts posts = postsRepository.findById(1L).get();

        // then
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @DisplayName("3. 시간이 자동으로 추가된다.")
    @Test
    public void test_3() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 5, 7, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        System.out.println("posts.getCreatedDate() = " + posts.getCreatedDate());
        System.out.println("posts.getModifiedDate() = " + posts.getModifiedDate());

        // isBefore() : 인자보다 과거일 때 true 리턴
        // isAfter() : 인자보다 미래일 때 true 리턴
        // isEqual() : 인자와 같을 때 true 리턴
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
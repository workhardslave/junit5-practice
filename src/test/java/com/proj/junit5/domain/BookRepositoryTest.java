package com.proj.junit5.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    /**
     * 데이터 등록 과정
     * 클라이언트 -> BookApiController -> BookSaveRequestDto -> BookService -> Book -> BookRepository
     */
    @DisplayName("책을 등록한다.")
    @Test
    void 책_등록_test() {
        // given (데이터 준비)
        final String title = "junit5";
        final String author = "workhardslave";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book savedBook = bookRepository.save(book);

        // then (검증)
        assertEquals(1L, savedBook.getId());
        assertEquals(title, savedBook.getTitle());
        assertEquals(author, savedBook.getAuthor());
    }

    // 목록

    // 단일

    // 수정

    // 삭제제
}
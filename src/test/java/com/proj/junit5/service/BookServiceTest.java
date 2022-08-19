package com.proj.junit5.service;

import com.proj.junit5.domain.Book;
import com.proj.junit5.domain.BookRepository;
import com.proj.junit5.util.MailSender;
import com.proj.junit5.web.dto.BookResponseDto;
import com.proj.junit5.web.dto.BookSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    /**
     * bookService 객체에 가짜 bookRepository, mailSender의 의존성 주입이 이루어진다.
     */
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @DisplayName("책을 등록한다.")
    @Test
    void 책_등록_test() {
        // given(데이터 준비)
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setTitle("junit5");
        dto.setAuthor("workhardslave");
        dto.setIsbn("123-456-7890");

        // stub(가설)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);
        
        // when(테스트 실행)
        BookResponseDto bookResponseDto = bookService.saveBook(dto);

        // then(검증)
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(bookResponseDto.getIsbn()).isEqualTo(dto.getIsbn());
    }
    
    @DisplayName("책 목록을 조회한다.")
    @Test
    void 책_목록_조회_test() {
        // given(데이터 준비)
        List<Book> bookList = Arrays.asList(
                new Book(1L, "junit5", "workhardslave", "1234-5678-90"),
                new Book(2L, "spring", "hsh", "5678-1234-90")
        );

        // stub(가설)
        when(bookRepository.findAll()).thenReturn(bookList);
                
        // when(테스트 실행)
        List<BookResponseDto> foundBookList = bookService.findAllBooks();

        // then(검증)
        assertThat(foundBookList.size()).isEqualTo(2);
        assertThat(foundBookList.get(0).getTitle()).isEqualTo("junit5");
        assertThat(foundBookList.get(0).getAuthor()).isEqualTo("workhardslave");
        assertThat(foundBookList.get(0).getIsbn()).isEqualTo("1234-5678-90");
        assertThat(foundBookList.get(1).getTitle()).isEqualTo("spring");
        assertThat(foundBookList.get(1).getAuthor()).isEqualTo("hsh");
        assertThat(foundBookList.get(1).getIsbn()).isEqualTo("5678-1234-90");
    }
}
package com.proj.junit5.service;

import com.proj.junit5.domain.Book;
import com.proj.junit5.domain.BookRepository;
import com.proj.junit5.web.dto.BookResponseDto;
import com.proj.junit5.web.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 등록
    public BookResponseDto saveBook(BookSaveRequestDto dto) {
        Book savedBook = bookRepository.save(dto.toEntity()); // 영속화된 객체는 서비스 레이어에서 빠져나가지 않도록 해야한다.
        return new BookResponseDto().toDto(savedBook);
    }

    // 목록
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAllBooks() {
        return bookRepository.findAll().stream() // 1. 스트림 생성
                .map(book -> new BookResponseDto().toDto(book)) // 2. Book의 인스턴스를 복사하여 BookResponseDto로 변환
                .collect(Collectors.toList()); // 3. 변환된 BookResponseDto를 리스트 형태로 변환하여 리턴
    }

    // 단건
    @Transactional(readOnly = true)
    public BookResponseDto findBookById(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 id 입니다. " + id));
        return new BookResponseDto().toDto(foundBook);
    }

    // 삭제

    // 수정정
}

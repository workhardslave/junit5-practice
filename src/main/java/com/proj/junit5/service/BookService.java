package com.proj.junit5.service;

import com.proj.junit5.domain.Book;
import com.proj.junit5.domain.BookRepository;
import com.proj.junit5.util.MailSender;
import com.proj.junit5.web.dto.BookResponseDto;
import com.proj.junit5.web.dto.BookSaveRequestDto;
import com.proj.junit5.web.dto.BookUpdateRequestDto;
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
    private final MailSender mailSender;

    /**
     * 책 저장
     * @param dto
     * @return
     */
    public BookResponseDto saveBook(BookSaveRequestDto dto) {
        Book savedBook = bookRepository.save(dto.toEntity()); // 영속화된 객체는 서비스 레이어에서 빠져나가지 않도록 해야한다.
        if (!mailSender.send()) {
            throw new RuntimeException("메일이 전송되지 않았습니다.");
        }
        return new BookResponseDto().toDto(savedBook);
    }

    /**
     * 책 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAllBooks() {
        return bookRepository.findAll().stream() // 1. 스트림 생성
                .map(book -> new BookResponseDto().toDto(book)) // 2. Book의 인스턴스를 복사하여 BookResponseDto로 변환
                .collect(Collectors.toList()); // 3. 변환된 BookResponseDto를 리스트 형태로 변환하여 리턴
    }

    /**
     * 책 단건 조회
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public BookResponseDto findBookById(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 id 입니다. " + id));
        return new BookResponseDto().toDto(foundBook);
    }

    /**
     * 책 삭제
     * @param id
     */
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * 책 수정
     * @param dto
     */
    public void updateBook(BookUpdateRequestDto dto) {
        Book foundBook = bookRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 id 입니다. " + dto.getId()));
        foundBook.update(dto); // Dirty Check
    }
}

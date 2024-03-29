package com.proj.junit5.web.dto;

import com.proj.junit5.domain.Book;
import lombok.Getter;

@Getter
public class BookResponseDto {

    private Long id;
    private String title;
    private String author;
    private String isbn;

    public BookResponseDto toDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        return this;
    }
}

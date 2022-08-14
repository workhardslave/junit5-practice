package com.proj.junit5.domain;

import com.proj.junit5.web.dto.BookUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 20, nullable = false)
    private String author;
    @Column(length = 30, nullable = false)
    private String isbn;

    @Builder
    public Book(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public void update(BookUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
    }
}

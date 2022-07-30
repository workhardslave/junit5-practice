package com.proj.junit5.web.dto;

import com.proj.junit5.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSaveRequestDto {

    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}

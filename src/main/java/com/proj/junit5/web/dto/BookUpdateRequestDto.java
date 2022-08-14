package com.proj.junit5.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookUpdateRequestDto {

    private Long id;
    private String title;
    private String author;
}

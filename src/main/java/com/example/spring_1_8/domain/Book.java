package com.example.spring_1_8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Genre genre;
    private Integer publicationYear;
}

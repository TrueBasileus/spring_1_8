package com.example.spring_1_8.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
public class Author {

    private Long id;
    private String name;
}

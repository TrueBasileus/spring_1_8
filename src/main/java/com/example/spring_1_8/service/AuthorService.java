package com.example.spring_1_8.service;

import com.example.spring_1_8.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAllAuthors();
    Optional<Author> getAuthorById(Long id);
    List<Author> findAuthorsByName(String namePart);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);

}

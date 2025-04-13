package com.example.spring_1_8.dao;

import com.example.spring_1_8.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author save(Author author);
    void deleteById(Long id);
    List<Author> findByNameContaining(String namePart);
}
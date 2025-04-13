package com.example.spring_1_8.dao;

import com.example.spring_1_8.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void deleteById(Long id);
}

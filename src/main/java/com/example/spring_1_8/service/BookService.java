package com.example.spring_1_8.service;

import com.example.spring_1_8.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
}

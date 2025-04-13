package com.example.spring_1_8.shell;

import com.example.spring_1_8.domain.Book;

import java.util.List;

public interface LibraryCommands {

    List<Book> listBooks();

    String addBook(
           String title,
           String authorName,
           String genreName,
           Integer year);

    String getBook(Long id);
    String deleteBook(Long id);

   String updateBook(
            Long id,
            String title,
            String authorName,
            String genreName,
            Integer year);
}
package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.BookDao;
import com.example.spring_1_8.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookDao.findById(id);
    }

    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }
}
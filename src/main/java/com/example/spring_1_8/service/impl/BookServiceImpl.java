package com.example.spring_1_8.service.impl;

import com.example.spring_1_8.dao.BookDao;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }
}
package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.BookDao;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.profiles.active=test")
class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Autowired
    private BookServiceImpl bookService;

    private final Long BOOK_ID = 10000L;

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle("War and Peace");
        when(bookDao.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("War and Peace");
        verify(bookDao).findAll();
    }

    @Test
    void getBookById_WithExistingId_ShouldReturnBook() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle("War and Peace");
        when(bookDao.findById(BOOK_ID)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(BOOK_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("War and Peace");
        verify(bookDao).findById(BOOK_ID);
    }

    @Test
    void saveBook_ShouldReturnSavedBook() {
        Book bookToSave = new Book();
        bookToSave.setTitle("New Book");

        Book savedBook = new Book();
        savedBook.setId(BOOK_ID);
        savedBook.setTitle("New Book");
        when(bookDao.save(bookToSave)).thenReturn(savedBook);

        Book result = bookService.saveBook(bookToSave);

        assertThat(result.getId()).isEqualTo(BOOK_ID);
        verify(bookDao).save(bookToSave);
    }

    @Test
    void deleteBook_ShouldCallDao() {
        bookService.deleteBook(BOOK_ID);

        verify(bookDao).deleteById(BOOK_ID);
    }
}
package com.example.spring_1_8;

import com.example.spring_1_8.dao.BookDao;
import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class BookDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private BookDao bookDao;

    @Test
    void findAll_ShouldReturnAllBooks() {
        bookDao = new BookDao(jdbcTemplate);
        var books = bookDao.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    void findById_WithValidId_ShouldReturnBook() {
        bookDao = new BookDao(jdbcTemplate);
        Optional<Book> book = bookDao.findById(1L);
        assertThat(book).isPresent();
        assertThat(book.get().getTitle()).isEqualTo("War and Peace");
    }

    @Test
    @Rollback
    void save_NewBook_ShouldInsert() {
        bookDao = new BookDao(jdbcTemplate);

        Book newBook = new Book();
        newBook.setTitle("New Book");

        Author author = new Author();
        author.setId(1L);
        newBook.setAuthor(author);

        Genre genre = new Genre();
        genre.setId(1L);
        newBook.setGenre(genre);

        newBook.setPublicationYear(2023);

        Book savedBook = bookDao.save(newBook);
        assertThat(savedBook.getId()).isNotNull();

        Optional<Book> foundBook = bookDao.findById(savedBook.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("New Book");
    }

    @Rollback
    @Test
    void deleteById_WithValidId_ShouldRemoveBook() {
        bookDao = new BookDao(jdbcTemplate);

        Optional<Book> bookBeforeDelete = bookDao.findById(1L);
        assertThat(bookBeforeDelete).isPresent();
        assertThat(bookBeforeDelete.get().getTitle()).isEqualTo("War and Peace");

        bookDao.deleteById(1L);

        Optional<Book> bookAfterDelete = bookDao.findById(1L);
        assertThat(bookAfterDelete).isEmpty();
    }

}
package com.example.spring_1_8.dao;

import com.example.spring_1_8.dao.impl.BookDaoImpl;
import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {
    @Autowired
    private BookDaoImpl bookDao;

    @Test
    void findAll_ShouldReturnAllBooks() {
        var books = bookDao.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    void findById_WithValidId_ShouldReturnBook() {
        Optional<Book> book = bookDao.findById(1L);
        assertThat(book).isPresent();
        assertThat(book.get().getTitle()).isEqualTo("War and Peace");
    }

    @Test
    @Rollback
    void save_NewBook_ShouldInsert() {
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
        Optional<Book> bookBeforeDelete = bookDao.findById(1L);
        assertThat(bookBeforeDelete).isPresent();
        assertThat(bookBeforeDelete.get().getTitle()).isEqualTo("War and Peace");

        bookDao.deleteById(1L);

        Optional<Book> bookAfterDelete = bookDao.findById(1L);
        assertThat(bookAfterDelete).isEmpty();
    }

}
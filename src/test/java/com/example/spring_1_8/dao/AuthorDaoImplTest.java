package com.example.spring_1_8.dao;

import com.example.spring_1_8.dao.impl.AuthorDaoImpl;
import com.example.spring_1_8.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {
    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    void findAll_ShouldReturnAllAuthors() {
        var authors = authorDao.findAll();
        assertThat(authors).hasSize(1);
    }

    @Test
    void findById_WithValidId_ShouldReturnAuthor() {
        Optional<Author> author = authorDao.findById(1L);
        assertThat(author).isPresent();
        assertThat(author.get().getName()).isEqualTo("Leo Tolstoy");
    }

    @Rollback
    @Test
    void save_NewAuthor_ShouldInsert() {
        Author newAuthor = new Author();
        newAuthor.setName("New Author");

        Author savedAuthor = authorDao.save(newAuthor);
        assertThat(savedAuthor.getId()).isNotNull();

        Optional<Author> foundAuthor = authorDao.findById(savedAuthor.getId());
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("New Author");
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingAuthors() {
        List<Author> authors = authorDao.findByNameContaining("Tol");
        assertThat(authors).hasSize(1);
        assertThat(authors.get(0).getName()).contains("Tolstoy");
    }
}
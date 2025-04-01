package com.example.spring_1_8;


import com.example.spring_1_8.dao.AuthorDao;
import com.example.spring_1_8.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class AuthorDaoTest {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private AuthorDao authorDao;

    @Test
    void findAll_ShouldReturnAllAuthors() {
        authorDao = new AuthorDao(jdbcTemplate);
        var authors = authorDao.findAll();
        assertThat(authors).hasSize(1);
    }

    @Test
    void findById_WithValidId_ShouldReturnAuthor() {
        authorDao = new AuthorDao(jdbcTemplate);
        Optional<Author> author = authorDao.findById(1L);
        assertThat(author).isPresent();
        assertThat(author.get().getName()).isEqualTo("Leo Tolstoy");
    }

    @Test
    void save_NewAuthor_ShouldInsert() {
        authorDao = new AuthorDao(jdbcTemplate);

        Author newAuthor = new Author();
        newAuthor.setName("New Author");
        newAuthor.setBirthDate(LocalDate.of(1990, 1, 1));

        Author savedAuthor = authorDao.save(newAuthor);
        assertThat(savedAuthor.getId()).isNotNull();

        Optional<Author> foundAuthor = authorDao.findById(savedAuthor.getId());
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("New Author");
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingAuthors() {
        authorDao = new AuthorDao(jdbcTemplate);
        List<Author> authors = authorDao.findByNameContaining("Tol");
        assertThat(authors).hasSize(1);
        assertThat(authors.get(0).getName()).contains("Tolstoy");
    }
}
package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.AuthorDao;
import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.profiles.active=test")
class AuthorServiceImplTest {

    @MockBean
    private AuthorDao authorDao;

    @Autowired
    private AuthorServiceImpl authorService;

    private final Long AUTHOR_ID = 10000L;

    @Test
    void getAllAuthors_ShouldReturnAllAuthors() {
        Author author = new Author(AUTHOR_ID, "Leo Tolstoy");
        when(authorDao.findAll()).thenReturn(List.of(author));

        List<Author> result = authorService.getAllAuthors();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Leo Tolstoy");
        verify(authorDao).findAll();
    }

    @Test
    void getAuthorById_WithExistingId_ShouldReturnAuthor() {
        Author author = new Author(AUTHOR_ID, "Leo Tolstoy");
        when(authorDao.findById(AUTHOR_ID)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(AUTHOR_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Leo Tolstoy");
        verify(authorDao).findById(AUTHOR_ID);
    }

    @Test
    void findAuthorsByName_ShouldReturnMatchingAuthors() {
        Author author = new Author(AUTHOR_ID, "Leo Tolstoy");
        when(authorDao.findByNameContaining("Tolstoy")).thenReturn(List.of(author));

        List<Author> result = authorService.findAuthorsByName("Tolstoy");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains("Tolstoy");
        verify(authorDao).findByNameContaining("Tolstoy");
    }

    @Test
    void saveAuthor_ShouldReturnSavedAuthor() {
        Author authorToSave = new Author(null, "New Author");
        Author savedAuthor = new Author(AUTHOR_ID, "New Author");
        when(authorDao.save(authorToSave)).thenReturn(savedAuthor);

        Author result = authorService.saveAuthor(authorToSave);

        assertThat(result.getId()).isEqualTo(AUTHOR_ID);
        verify(authorDao).save(authorToSave);
    }

    @Test
    void deleteAuthor_ShouldCallDao() {
        authorService.deleteAuthor(AUTHOR_ID);

        verify(authorDao).deleteById(AUTHOR_ID);
    }
}
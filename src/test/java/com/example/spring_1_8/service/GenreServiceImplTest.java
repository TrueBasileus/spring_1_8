package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.GenreDao;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.profiles.active=test")
class GenreServiceImplTest {

    @MockBean
    private GenreDao genreDao;

    @Autowired
    private GenreServiceImpl genreService;

    private final Long GENRE_ID = 10000L;

    @Test
    void getAllGenres_ShouldReturnAllGenres() {
        Genre genre = new Genre(GENRE_ID, "Novel");
        when(genreDao.findAll()).thenReturn(List.of(genre));

        List<Genre> result = genreService.getAllGenres();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Novel");
        verify(genreDao).findAll();
    }

    @Test
    void getGenreById_WithExistingId_ShouldReturnGenre() {
        Genre genre = new Genre(GENRE_ID, "Novel");
        when(genreDao.findById(GENRE_ID)).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.getGenreById(GENRE_ID);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Novel");
        verify(genreDao).findById(GENRE_ID);
    }

    @Test
    void getGenreByName_WithExistingName_ShouldReturnGenre() {
        Genre genre = new Genre(GENRE_ID, "Novel");
        when(genreDao.findByName("Novel")).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.getGenreByName("Novel");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Novel");
        verify(genreDao).findByName("Novel");
    }

    @Test
    void saveGenre_ShouldReturnSavedGenre() {
        Genre genreToSave = new Genre(null, "New Genre");
        Genre savedGenre = new Genre(GENRE_ID, "New Genre");
        when(genreDao.save(genreToSave)).thenReturn(savedGenre);

        Genre result = genreService.saveGenre(genreToSave);

        assertThat(result.getId()).isEqualTo(GENRE_ID);
        verify(genreDao).save(genreToSave);
    }

    @Test
    void deleteGenre_ShouldCallDao() {
        genreService.deleteGenre(GENRE_ID);

        verify(genreDao).deleteById(GENRE_ID);
    }
}
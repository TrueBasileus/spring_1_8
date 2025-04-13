package com.example.spring_1_8.dao;

import com.example.spring_1_8.dao.impl.GenreDaoImpl;
import com.example.spring_1_8.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {
    @Autowired
    private GenreDaoImpl genreDao;

    @Test
    void findAll_ShouldReturnAllGenres() {
        var genres = genreDao.findAll();
        assertThat(genres).hasSize(1);
    }

    @Test
    void findById_WithValidId_ShouldReturnGenre() {
        Optional<Genre> genre = genreDao.findById(1L);
        assertThat(genre).isPresent();
        assertThat(genre.get().getName()).isEqualTo("Novel");
    }

    @Test
    void findByName_WithValidName_ShouldReturnGenre() {
        Optional<Genre> genre = genreDao.findByName("Novel");
        assertThat(genre).isPresent();
        assertThat(genre.get().getId()).isEqualTo(1L);
    }

    @Test
    void save_NewGenre_ShouldInsert() {
        Genre newGenre = new Genre();
        newGenre.setName("New Genre");

        Genre savedGenre = genreDao.save(newGenre);
        assertThat(savedGenre.getId()).isNotNull();

        Optional<Genre> foundGenre = genreDao.findById(savedGenre.getId());
        assertThat(foundGenre).isPresent();
        assertThat(foundGenre.get().getName()).isEqualTo("New Genre");
    }
}
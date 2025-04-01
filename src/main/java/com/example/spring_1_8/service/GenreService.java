package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.GenreDao;
import com.example.spring_1_8.domain.Genre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreDao genreDao;
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreDao.findById(id);
    }

    public Optional<Genre> getGenreByName(String name) {
        return genreDao.findByName(name);
    }

    public Genre saveGenre(Genre genre) {
        return genreDao.save(genre);
    }

    public void deleteGenre(Long id) {
        genreDao.deleteById(id);
    }
}
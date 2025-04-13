package com.example.spring_1_8.service;

import com.example.spring_1_8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAllGenres();

    Optional<Genre> getGenreById(Long id);

    Optional<Genre> getGenreByName(String name);
    Genre saveGenre(Genre genre);
    void deleteGenre(Long id);
}

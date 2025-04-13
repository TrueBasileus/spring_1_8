package com.example.spring_1_8.service.impl;

import com.example.spring_1_8.dao.GenreDao;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;
    @Override
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }
    @Override
    public Optional<Genre> getGenreById(Long id) {
        return genreDao.findById(id);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return genreDao.findByName(name);
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public void deleteGenre(Long id) {
        genreDao.deleteById(id);
    }
}
package com.example.spring_1_8.dao;

import com.example.spring_1_8.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Optional<Genre> findByName(String name);
    Genre save(Genre genre);
    void deleteById(Long id);
}

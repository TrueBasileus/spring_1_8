package com.example.spring_1_8.dao;

import com.example.spring_1_8.domain.Genre;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public List<Genre> findAll() {
        String sql = "SELECT id, name FROM genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getLong("id"));
            genre.setName(rs.getString("name"));
            return genre;
        });
    }

    public Optional<Genre> findById(Long id) {
        String sql = "SELECT id, name FROM genres WHERE id = :id";
        return jdbcTemplate.query(sql, Map.of("id", id), rs -> {
            if (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getLong("id"));
                genre.setName(rs.getString("name"));
                return Optional.of(genre);
            }
            return Optional.empty();
        });
    }

    public Optional<Genre> findByName(String name) {
        String sql = "SELECT id, name FROM genres WHERE name = :name";
        return jdbcTemplate.query(sql, Map.of("name", name), rs -> {
            if (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getLong("id"));
                genre.setName(rs.getString("name"));
                return Optional.of(genre);
            }
            return Optional.empty();
        });
    }

    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            return insert(genre);
        } else {
            return update(genre);
        }
    }

    private Genre insert(Genre genre) {
        String sql = "INSERT INTO genres (name) VALUES (:name)";
        jdbcTemplate.update(sql, Map.of("name", genre.getName()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long id = jdbcTemplate.queryForObject("CALL IDENTITY()", Map.of(), Long.class);
        genre.setId(id);
        return genre;
    }

    private Genre update(Genre genre) {
        String sql = "UPDATE genres SET name = :name WHERE id = :id";
        Map<String, Object> params = Map.of(
                "id", genre.getId(),
                "name", genre.getName()
        );
        jdbcTemplate.update(sql, params);
        return genre;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM genres WHERE id = :id";
        jdbcTemplate.update(sql, Map.of("id", id));
    }
}
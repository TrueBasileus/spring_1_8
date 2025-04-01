package com.example.spring_1_8.dao;

import com.example.spring_1_8.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Author> findAll() {
        String sql = "SELECT id, name, birth_date FROM authors";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setBirthDate(rs.getDate("birth_date").toLocalDate());
            return author;
        });
    }

    public Optional<Author> findById(Long id) {
        String sql = "SELECT id, name, birth_date FROM authors WHERE id = :id";
        return jdbcTemplate.query(sql, Map.of("id", id), rs -> {
            if (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setName(rs.getString("name"));
                author.setBirthDate(rs.getDate("birth_date").toLocalDate());
                return Optional.of(author);
            }
            return Optional.empty();
        });
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            return insert(author);
        } else {
            return update(author);
        }
    }

    private Author insert(Author author) {
        String sql = "INSERT INTO authors (name, birth_date) VALUES (:name, :birthDate)";
        Map<String, Object> params = Map.of(
                "name", author.getName(),
                "birthDate", author.getBirthDate()
        );
        jdbcTemplate.update(sql, params);

        Long id = jdbcTemplate.queryForObject("CALL IDENTITY()", Map.of(), Long.class);
        author.setId(id);
        return author;
    }

    private Author update(Author author) {
        String sql = "UPDATE authors SET name = :name, birth_date = :birthDate WHERE id = :id";
        Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName(),
                "birthDate", author.getBirthDate()
        );
        jdbcTemplate.update(sql, params);
        return author;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM authors WHERE id = :id";
        jdbcTemplate.update(sql, Map.of("id", id));
    }

    public List<Author> findByNameContaining(String namePart) {
        String sql = "SELECT id, name, birth_date FROM authors WHERE name LIKE :namePart";
        return jdbcTemplate.query(sql,
                Map.of("namePart", "%" + namePart + "%"),
                (rs, rowNum) -> {
                    Author author = new Author();
                    author.setId(rs.getLong("id"));
                    author.setName(rs.getString("name"));
                    author.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    return author;
                });
    }
}
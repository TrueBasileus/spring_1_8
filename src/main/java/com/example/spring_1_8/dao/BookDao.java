package com.example.spring_1_8.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.spring_1_8.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Book> findAll() {
        String sql = "SELECT b.id, b.title, b.publication_year, " +
                "a.id as author_id, a.name as author_name, a.birth_date, " +
                "g.id as genre_id, g.name as genre_name " +
                "FROM books b " +
                "JOIN authors a ON b.author_id = a.id " +
                "JOIN genres g ON b.genre_id = g.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setPublicationYear(rs.getInt("publication_year"));

            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setName(rs.getString("author_name"));
            author.setBirthDate(rs.getDate("birth_date").toLocalDate());
            book.setAuthor(author);

            Genre genre = new Genre();
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("genre_name"));
            book.setGenre(genre);

            return book;
        });
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT b.id, b.title, b.publication_year, " +
                "a.id as author_id, a.name as author_name, a.birth_date, " +
                "g.id as genre_id, g.name as genre_name " +
                "FROM books b " +
                "JOIN authors a ON b.author_id = a.id " +
                "JOIN genres g ON b.genre_id = g.id " +
                "WHERE b.id = :id";
        return jdbcTemplate.query(sql, Map.of("id", id), rs -> {
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setPublicationYear(rs.getInt("publication_year"));

                Author author = new Author();
                author.setId(rs.getLong("author_id"));
                author.setName(rs.getString("author_name"));
                author.setBirthDate(rs.getDate("birth_date").toLocalDate());
                book.setAuthor(author);

                Genre genre = new Genre();
                genre.setId(rs.getLong("genre_id"));
                genre.setName(rs.getString("genre_name"));
                book.setGenre(genre);

                return Optional.of(book);
            }
            return Optional.empty();
        });
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            return insert(book);
        } else {
            return update(book);
        }
    }

    private Book insert(Book book) {
        String sql = "INSERT INTO books (title, author_id, genre_id, publication_year) " +
                "VALUES (:title, :authorId, :genreId, :publicationYear)";
        Map<String, Object> params = Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId(),
                "publicationYear", book.getPublicationYear()
        );

        jdbcTemplate.update(sql, params);

        Long id = jdbcTemplate.queryForObject("CALL IDENTITY()", Map.of(), Long.class);
        book.setId(id);
        return book;
    }

    private Book update(Book book) {
        String sql = "UPDATE books SET title = :title, author_id = :authorId, " +
                "genre_id = :genreId, publication_year = :publicationYear " +
                "WHERE id = :id";
        Map<String, Object> params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId(),
                "publicationYear", book.getPublicationYear()
        );
        jdbcTemplate.update(sql, params);
        return book;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = :id";
        jdbcTemplate.update(sql, Map.of("id", id));
    }
}
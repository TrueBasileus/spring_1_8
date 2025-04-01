package com.example.spring_1_8.service;

import com.example.spring_1_8.dao.AuthorDao;
import com.example.spring_1_8.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;

    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorDao.findById(id);
    }

    public List<Author> findAuthorsByName(String namePart) {
        return authorDao.findByNameContaining(namePart);
    }

    public Author saveAuthor(Author author) {
        return authorDao.save(author);
    }

    public void deleteAuthor(Long id) {
        authorDao.deleteById(id);
    }
}
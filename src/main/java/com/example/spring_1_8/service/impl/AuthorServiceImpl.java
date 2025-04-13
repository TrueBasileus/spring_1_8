package com.example.spring_1_8.service.impl;

import com.example.spring_1_8.dao.AuthorDao;
import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorDao.findById(id);
    }

    @Override
    public List<Author> findAuthorsByName(String namePart) {
        return authorDao.findByNameContaining(namePart);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorDao.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorDao.deleteById(id);
    }
}
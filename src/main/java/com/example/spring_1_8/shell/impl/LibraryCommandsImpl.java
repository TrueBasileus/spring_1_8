package com.example.spring_1_8.shell.impl;


import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.AuthorService;
import com.example.spring_1_8.service.BookService;
import com.example.spring_1_8.service.GenreService;
import com.example.spring_1_8.shell.LibraryCommands;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class LibraryCommandsImpl implements LibraryCommands {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    @Override
    @ShellMethod(key = "books list", value = "List all books")
    public List<Book> listBooks() {
        return bookService.getAllBooks();
    }
    @Override
    @ShellMethod(key = "books add", value = "Add a new book")
    public String addBook(
            @ShellOption({"-t", "--title"}) String title,
            @ShellOption({"-a", "--author-name"}) String authorName,
            @ShellOption({"-g", "--genre-name"}) String genreName,
            @ShellOption(value = {"-y", "--year"}, defaultValue = ShellOption.NULL) Integer year) {

        Book book = new Book();
        book.setTitle(title);

        List<Author> authors = authorService.findAuthorsByName(authorName);
        if(authors.isEmpty()) {
            Author author = new Author();
            author.setName(authorName);
            Author savedAuthor = authorService.saveAuthor(author);
            book.setAuthor(savedAuthor);
        } else {
            book.setAuthor(authors.get(0));
        }

        Optional<Genre> genre = genreService.getGenreByName(genreName);
        if(genre.isEmpty()){
            Genre newGenre = new Genre();
            newGenre.setName(genreName);
            Genre savedGenre = genreService.saveGenre(newGenre);
            book.setGenre(savedGenre);
        } else {
            book.setGenre(genre.get());
        }

        book.setPublicationYear(year);

        Book savedBook = bookService.saveBook(book);
        return "Book added with ID: " + savedBook.getId();
    }
    @Override
    @ShellMethod(key = "books get", value = "Get book by ID")
    public String getBook(@ShellOption({"-i", "--id"}) Long id) {
        return bookService.getBookById(id)
                .map(Book::toString)
                .orElse("Book not found with ID: " + id);
    }
    @Override
    @ShellMethod(key = "books delete", value = "Delete book by ID")
    public String deleteBook(@ShellOption({"-i", "--id"}) Long id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return "Book deleted with ID: " + id;
        }
        return "Book not found with ID: " + id;
    }
    @Override
    @ShellMethod(key = "books update", value = "Update a book")
    public String updateBook(
            @ShellOption({"-i", "--id"}) Long id,
            @ShellOption(value = {"-t", "--title"}, defaultValue = ShellOption.NULL) String title,
            @ShellOption(value = {"-a", "--author-name"}, defaultValue = ShellOption.NULL) String authorName,
            @ShellOption(value = {"-g", "--genre-name"}, defaultValue = ShellOption.NULL) String genreName,
            @ShellOption(value = {"-y", "--year"}, defaultValue = ShellOption.NULL)
            Integer year) {

        return bookService.getBookById(id).map(book -> {
            if (title != null) book.setTitle(title);
            if (authorName != null) {
                List<Author> authors = authorService.findAuthorsByName(authorName);
                if(authors.isEmpty()) {
                    Author author = new Author();
                    author.setName(authorName);
                    Author savedAuthor = authorService.saveAuthor(author);
                    book.setAuthor(savedAuthor);
                } else {
                    book.setAuthor(authors.get(0));
                }
            }
            if (genreName != null) {
                Optional<Genre> genre = genreService.getGenreByName(genreName);
                if(genre.isEmpty()){
                    Genre newGenre = new Genre();
                    newGenre.setName(genreName);
                    Genre savedGenre = genreService.saveGenre(newGenre);
                    book.setGenre(savedGenre);
                } else {
                    book.setGenre(genre.get());
                }
            }
            if (year != null) book.setPublicationYear(year);

            bookService.saveBook(book);
            return "Book updated: " + book;
        }).orElse("Book not found with ID: " + id);
    }
}
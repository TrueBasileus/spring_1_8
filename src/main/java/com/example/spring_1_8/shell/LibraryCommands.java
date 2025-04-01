package com.example.spring_1_8.shell;


import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class LibraryCommands {
    private final BookService bookService;
    @ShellMethod(key = "books list", value = "List all books")
    public List<Book> listBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(key = "books add", value = "Add a new book")
    public String addBook(
            @ShellOption({"-t", "--title"}) String title,
            @ShellOption({"-a", "--author-id"}) Long authorId,
            @ShellOption({"-g", "--genre-id"}) Long genreId,
            @ShellOption(value = {"-y", "--year"}, defaultValue = ShellOption.NULL) Integer year) {

        Book book = new Book();
        book.setTitle(title);

        Author author = new Author();
        author.setId(authorId);
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setId(genreId);
        book.setGenre(genre);

        book.setPublicationYear(year);

        bookService.saveBook(book);
        return "Book added with ID: " + book.getId();
    }

    @ShellMethod(key = "books get", value = "Get book by ID")
    public String getBook(@ShellOption({"-i", "--id"}) Long id) {
        return bookService.getBookById(id)
                .map(Book::toString)
                .orElse("Book not found with ID: " + id);
    }

    @ShellMethod(key = "books delete", value = "Delete book by ID")
    public String deleteBook(@ShellOption({"-i", "--id"}) Long id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return "Book deleted with ID: " + id;
        }
        return "Book not found with ID: " + id;
    }

    @ShellMethod(key = "books update", value = "Update a book")
    public String updateBook(
            @ShellOption({"-i", "--id"}) Long id,
            @ShellOption(value = {"-t", "--title"}, defaultValue = ShellOption.NULL) String title,
            @ShellOption(value = {"-a", "--author-id"}, defaultValue = ShellOption.NULL) Long authorId,
            @ShellOption(value = {"-g", "--genre-id"}, defaultValue = ShellOption.NULL) Long genreId,
            @ShellOption(value = {"-y", "--year"}, defaultValue = ShellOption.NULL) Integer year) {

        return bookService.getBookById(id).map(book -> {
            if (title != null) book.setTitle(title);
            if (authorId != null) {
                Author author = new Author();
                author.setId(authorId);
                book.setAuthor(author);
            }
            if (genreId != null) {
                Genre genre = new Genre();
                genre.setId(genreId);
                book.setGenre(genre);
            }
            if (year != null) book.setPublicationYear(year);

            bookService.saveBook(book);
            return "Book updated: " + book;
        }).orElse("Book not found with ID: " + id);
    }
}
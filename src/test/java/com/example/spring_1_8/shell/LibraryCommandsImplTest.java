package com.example.spring_1_8.shell;

import com.example.spring_1_8.domain.Author;
import com.example.spring_1_8.domain.Book;
import com.example.spring_1_8.domain.Genre;
import com.example.spring_1_8.service.AuthorService;
import com.example.spring_1_8.service.BookService;
import com.example.spring_1_8.service.GenreService;
import com.example.spring_1_8.shell.impl.LibraryCommandsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class LibraryCommandsImplTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private LibraryCommandsImpl libraryCommands;

    @Test
    void listBooks_ShouldReturnAllBooks() {
        // Подготовка
        Book book = new Book();
        book.setTitle("Test Book");
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        List<Book> result = libraryCommands.listBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
    }

    @Test
    void addBook_WithNewAuthorAndGenre_ShouldSaveAll() {
        when(authorService.findAuthorsByName("New Author")).thenReturn(List.of());
        when(genreService.getGenreByName("New Genre")).thenReturn(Optional.empty());

        Author savedAuthor = new Author();
        savedAuthor.setId(10L);
        when(authorService.saveAuthor(any())).thenReturn(savedAuthor);

        Genre savedGenre = new Genre();
        savedGenre.setId(10L);
        when(genreService.saveGenre(any())).thenReturn(savedGenre);

        Book savedBook = new Book();
        savedBook.setId(10L);
        when(bookService.saveBook(any())).thenReturn(savedBook);


        String result = libraryCommands.addBook(
                "New Book",
                "New Author",
                "New Genre",
                2023);


        assertThat(result).isEqualTo("Book added with ID: 10");
        verify(authorService).saveAuthor(any());
        verify(genreService).saveGenre(any());
        verify(bookService).saveBook(any());
    }

    @Test
    void getBook_WhenExists_ShouldReturnBookInfo() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Existing Book");
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));


        String result = libraryCommands.getBook(1L);


        assertThat(result).contains("Existing Book");
    }

    @Test
    void deleteBook_WhenExists_ShouldDelete() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(new Book()));

        String result = libraryCommands.deleteBook(1L);

        assertThat(result).isEqualTo("Book deleted with ID: 1");
        verify(bookService).deleteBook(1L);
    }

    @Test
    void updateBook_WithNewAuthor_ShouldUpdate() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(existingBook));

        when(authorService.findAuthorsByName("New Author")).thenReturn(List.of());
        Author savedAuthor = new Author();
        savedAuthor.setId(2L);
        when(authorService.saveAuthor(any())).thenReturn(savedAuthor);

        String result = libraryCommands.updateBook(
                1L,
                "Updated Title",
                "New Author",
                null,
                null);

        assertThat(result).contains("Book updated");
        assertThat(existingBook.getTitle()).isEqualTo("Updated Title");
        assertThat(existingBook.getAuthor().getId()).isEqualTo(2L);
    }
}
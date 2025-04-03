package ba.unsa.etf.book_service.book_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;


import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;
import ba.unsa.etf.book_service.book_service.services.BookService;

@ExtendWith(MockitoExtension.class)
class BookServiceApplicationTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks() {
    List<Book> books = List.of(
        Book.builder()
            .id(1L)
            .title("Book A")
            .author("Author A")
            .genre("Fiction")
            .publishedYear(2001L)
            .bookCopies(new ArrayList<>())
            .build(),

        Book.builder()
            .id(2L)
            .title("Book B")
            .author("Author B")
            .genre("Drama")
            .publishedYear(2010L)
            .bookCopies(new ArrayList<>())
            .build()
    );

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.GetAllBooks();

        assertThat(result).hasSize(2);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnBookByTitle() {
        Book book = new Book(1L, "Title1", "Author1", "Genre1", 2000L, new ArrayList<>());
        when(bookRepository.findByTitle("Title1")).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookByTitle("Title1");

        assertThat(result).isPresent();
        assertThat(result.get().getAuthor()).isEqualTo("Author1");
    }

    @Test
    void shouldCreateNewBook() {
        Book book = new Book(null, "TitleX", "AuthorX", "GenreX", 2020L, new ArrayList<>());
        Book savedBook = new Book(3L, "TitleX", "AuthorX", "GenreX", 2020L, new ArrayList<>());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.CreateNewBook("TitleX", "AuthorX", "GenreX", 2020L);

        assertThat(result.getId()).isEqualTo(3L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookById() {
        Long bookId = 1L;

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }
}

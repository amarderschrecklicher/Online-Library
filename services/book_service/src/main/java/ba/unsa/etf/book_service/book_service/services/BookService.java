package ba.unsa.etf.book_service.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.mappers.BookMapper;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> GetAllBooks() {
        return bookRepository.findAll();
    }

    public Book GetBookById(Long id) {
        return bookRepository.getById(id);
    }

    public BookDto GetBookByTitle(String title) {
        Book book =  bookRepository.findByTitle(title)
        .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));;
        return BookMapper.toDto(book);
    }

    public Book AddNewBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public  Book CreateNewBook(String title, String author, String genre, Long publisheYear) {
        Book book = Book.builder().title(title)
        .author(author)
        .genre(genre)
        .publishedYear(publisheYear)
        .bookCopies(new ArrayList<>())
        .build();
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.getById(id);

        bookRepository.save(existingBook);
        return existingBook;
    }

    public void deleteBook(Long id) {

        bookRepository.deleteById(id);
    }


}
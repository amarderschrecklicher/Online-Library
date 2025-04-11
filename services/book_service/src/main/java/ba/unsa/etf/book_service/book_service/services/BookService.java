package ba.unsa.etf.book_service.book_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Book> getAllBooksWithCopies() {
        return bookRepository.findAllWithCopies();
    }   

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
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

    public Book updateBook(Long id, BookDto updatedBook) {

        Book existingBook = bookRepository.findById(id).get();

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setPublishedYear(updatedBook.getPublishedYear());

        bookRepository.save(existingBook);
        return existingBook;
    }

    public void deleteBook(Long id) {

        bookRepository.deleteById(id);
    }

    public Page<Book> getAllBooksPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookRepository.findAll(pageable);
    }



}
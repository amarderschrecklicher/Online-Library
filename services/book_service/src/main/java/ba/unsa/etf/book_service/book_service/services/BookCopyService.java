package ba.unsa.etf.book_service.book_service.services;


import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.models.BookCopy;
import ba.unsa.etf.book_service.book_service.repositories.BookCopyRepository;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public Optional<BookCopy> getBookCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }

    public boolean existsByIsbn(String isbn) {
        return bookCopyRepository.existsByCode(isbn);
    }

    public BookCopy createBookCopy(BookCopyDto bookCopyDto) {
        Optional<Book> book = bookRepository.findById(bookCopyDto.getBookId());
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + bookCopyDto.getBookId() + " does not exist.");
        }

        BookCopy copy = BookCopy.builder()
            .code(bookCopyDto.getCode())
            .status(bookCopyDto.getStatus())
            .book(book.get())
            .build();

        return bookCopyRepository.save(copy);
    }

    public BookCopy updateBookCopy(Long id, BookCopyDto updatedCopyDto) {
        Optional<BookCopy> optionalCopy = bookCopyRepository.findById(id);
        if (optionalCopy.isEmpty()) {
            return null;
        }

        BookCopy existingCopy = optionalCopy.get();
        existingCopy.setCode(updatedCopyDto.getCode());
        existingCopy.setStatus(updatedCopyDto.getStatus());

        Optional<Book> book = bookRepository.findById(updatedCopyDto.getBookId());
        book.ifPresent(existingCopy::setBook);

        return bookCopyRepository.save(existingCopy);
    }

    public void deleteBookCopy(Long id) {
        bookCopyRepository.deleteById(id);
    }    
}

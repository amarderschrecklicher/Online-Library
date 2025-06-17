package ba.unsa.etf.book_service.book_service.services;


import ba.unsa.etf.book_service.book_service.config.RabbitConfig;
import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.dtos.BookReservedEvent;
import ba.unsa.etf.book_service.book_service.mappers.BookCopyMapper;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.models.BookCopy;
import ba.unsa.etf.book_service.book_service.repositories.BookCopyRepository;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;

import jakarta.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookCopyService(BookCopyRepository bookCopyRepository, BookRepository bookRepository, RabbitTemplate rabbitTemplate) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String generateCode() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String timestamp = LocalDateTime.now().format(formatter);
        String randomPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // 6 random alphanumeric chars
        return timestamp + "-" + randomPart;
    }

    public List<BookCopyDto> getAllBookCopies() {
        List<BookCopy> bookCopies = bookCopyRepository.findAll();
        return BookCopyMapper.toDto(bookCopies);
    }

    public Optional<BookCopy> getBookCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }

    public Optional<BookCopyDto> getFirstAvailableCopyByTitle(String title) {
        Optional<BookCopy> bookCopy =  bookCopyRepository.findFirstByBook_TitleAndAvailableTrue(title);
        return bookCopy.map(BookCopyMapper::toDto);

    }

    public boolean existsByCode(String isbn) {
        return bookCopyRepository.existsByCode(isbn);
    }

    public BookCopy createBookCopy(BookCopyDto bookCopyDto) {
        Optional<Book> book = bookRepository.findById(bookCopyDto.getBookId());
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + bookCopyDto.getBookId() + " does not exist.");
        }

        BookCopy copy = BookCopy.builder()
            .code(generateCode())
            .available(true)
            .book(book.get())
            .build();

        return bookCopyRepository.save(copy);
    }

    public BookCopy createBookCopyByTitle(String bookTitle) {

        if (bookRepository.existsByTitle(bookTitle)) {
            throw new IllegalArgumentException("Book with title " + bookTitle + " does not exist.");
        }

        Optional<Book> book = bookRepository.findByTitle(bookTitle);


        BookCopy copy = BookCopy.builder()
                .code(generateCode())
                .available(false)
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
        existingCopy.setAvailable(updatedCopyDto.getAvailable());

        Optional<Book> book = bookRepository.findById(updatedCopyDto.getBookId());
        book.ifPresent(existingCopy::setBook);

        return bookCopyRepository.save(existingCopy);
    }

    public void deleteBookCopy(Long id) {
        bookCopyRepository.deleteById(id);
    }
    
    public BookCopy updateStatus(Long id, Boolean newAvailable) {
        Optional<BookCopy> optionalCopy = bookCopyRepository.findById(id);
        if (optionalCopy.isEmpty()) return null;
    
        BookCopy copy = optionalCopy.get();
        copy.setAvailable(newAvailable);
        return bookCopyRepository.save(copy);
    }
    
    public void reserveBookCopy(Long bookId, Long memberId) {
        Optional<BookCopy> optionalCopy = bookCopyRepository.findById(bookId);
        if (optionalCopy.isEmpty()) return;
    
        BookCopy copy = optionalCopy.get();
        copy.setAvailable(false);
        bookCopyRepository.save(copy);

        BookReservedEvent event = new BookReservedEvent(bookId, memberId, UUID.randomUUID().toString());
    
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "membership.book.reserved", event);
    }

    public List<BookCopyDto> getAvailableBookCopies() {
        List<BookCopy> availableCopies = bookCopyRepository.findByAvailableTrue();
        if (availableCopies.isEmpty()) {
            return List.of(); // Return an empty list if no available copies found
        }
        return BookCopyMapper.toDto(availableCopies);
    }
}

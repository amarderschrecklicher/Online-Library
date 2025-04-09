package ba.unsa.etf.book_service.book_service.controllers;

import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.models.BookCopy;
import ba.unsa.etf.book_service.book_service.services.BookCopyService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/book-copy")
@CrossOrigin
public class BookCopyController {

    private final BookCopyService bookCopyService;

    @Autowired
    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @GetMapping
    public List<BookCopy> getAllBookCopies() {
        return bookCopyService.getAllBookCopies();
    }

    @PostMapping
    public ResponseEntity<?> createBookCopy(@Valid @RequestBody BookCopyDto bookCopyDto) {
        System.out.println("Creating new BookCopy!");

        if (bookCopyService.existsByIsbn(null)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book copy with this ISBN already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }

        BookCopy newBookCopy = bookCopyService.createBookCopy(bookCopyDto);
        return ResponseEntity.ok().body(newBookCopy);
    }

    @PutMapping("/{copyId}")
    public ResponseEntity<?> updateBookCopy(@PathVariable("copyId") Long id, @Valid @RequestBody BookCopyDto bookCopyDto) {
        if (bookCopyService.existsByIsbn(bookCopyDto.getIsbn())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book copy with this ISBN already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }

        try {
            BookCopy updatedCopy = bookCopyService.updateBookCopy(id, bookCopyDto);
            if (updatedCopy == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(updatedCopy);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{copyId}")
    public void deleteBookCopy(@PathVariable("copyId") Long id) {
        System.out.println("Delete BookCopy called!");
        bookCopyService.deleteBookCopy(id);
    }

}

package ba.unsa.etf.book_service.book_service.controllers;

import ba.unsa.etf.book_service.book_service.dtos.BookCopyDto;
import ba.unsa.etf.book_service.book_service.mappers.BookCopyMapper;
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
import java.util.Optional;


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
    public List<BookCopyDto> getAllBookCopies() {
        return bookCopyService.getAllBookCopies();
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getFirstAvailableCopyByTitle(@PathVariable("title") String title) {
        Optional<BookCopyDto> bookCopy = bookCopyService.getFirstAvailableCopyByTitle(title);
        if (bookCopy.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No book copy found with title " + title);
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }
        return ResponseEntity.ok().body(bookCopy);
    }

    @PostMapping
    public ResponseEntity<?> createBookCopy(@Valid @RequestBody BookCopyDto bookCopyDto) {
        System.out.println("Creating new BookCopy1!");

        BookCopy newBookCopy = bookCopyService.createBookCopy(bookCopyDto);

        return ResponseEntity.ok().body(BookCopyMapper.toDto(newBookCopy));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBookCopyByTitle(@Valid @RequestBody String bookTitle) {
        System.out.println("Creating new BookCopy2!");

        BookCopy newBookCopy = bookCopyService.createBookCopyByTitle(bookTitle);
        System.out.println(BookCopyMapper.toDto(newBookCopy));
        return ResponseEntity.ok().body(BookCopyMapper.toDto(newBookCopy));
    }

    @PutMapping("/{copyId}")
    public ResponseEntity<?> updateBookCopy(@PathVariable("copyId") Long id, @Valid @RequestBody BookCopyDto bookCopyDto) {
        if (bookCopyService.existsByCode(bookCopyDto.getCode())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book copy with this Code already exists");
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
    public ResponseEntity<?> deleteBookCopy(@PathVariable("copyId") Long id) {
        System.out.println("Delete BookCopy called!");
        try{
            bookCopyService.deleteBookCopy(id);
            Map<String,String> response = new HashMap<>();
            response.put("message","Book deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete book.");
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(error);
        }
        
    }

    @PatchMapping("/{copyId}/status")
    public ResponseEntity<?> updateBookCopyStatus(@PathVariable Long copyId, @RequestBody Map<String, Boolean> payload) {
        Boolean newStatus = payload.get("status");
    
        if (newStatus == null ) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Status is required");
            return ResponseEntity.badRequest().body(error);
        }
    
        BookCopy updatedCopy = bookCopyService.updateStatus(copyId, newStatus);
    
        if (updatedCopy == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book copy with ID " + copyId + " not found");
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(error);
        }
    
        return ResponseEntity.ok(updatedCopy);
    }    

}

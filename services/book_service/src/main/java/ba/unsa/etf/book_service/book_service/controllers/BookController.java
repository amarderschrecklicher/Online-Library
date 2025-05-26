package ba.unsa.etf.book_service.book_service.controllers;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.services.BookCopyService;
import ba.unsa.etf.book_service.book_service.services.BookService;
import jakarta.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
@CrossOrigin
class BookController {
/* 
    @Autowired
    private RestTemplate restTemplate;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;
*/
    private final BookService bookService;

    private final BookCopyService bookCopyService;

    @Autowired
    public BookController(BookService bookService, BookCopyService bookCopyService) {
        this.bookService = bookService; this.bookCopyService = bookCopyService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{bookId}")
    public Book getBookById(@PathVariable("bookId") Long id) {
        return bookService.getBookById(id);
    }
    
    
    @GetMapping("/paged")
    public Page<Book> getAllBooksPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "title") String sortBy
    ) {
        return bookService.getAllBooksPaged(page, size, sortBy);
    }

    @GetMapping(path="/by-title")
    public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
        Optional<Book> book = bookService.getBookByTitle(title);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto) {
        System.out.println("Creating new Book!");

        if (bookService.existsByTitle(bookDto.getTitle())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }

        Book newBook = bookService.CreateNewBook(
            bookDto.getTitle(),
            bookDto.getAuthor(),
            bookDto.getGenre(),
            bookDto.getPublishedYear()
        );
        
        return ResponseEntity.ok().body(newBook);
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable("bookId") Long id, @Valid @RequestBody BookDto updatedBookData){

        if (bookService.existsByTitle(updatedBookData.getTitle())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Book already exists");
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(error);
        }
        try{
            Book updatedBook = bookService.updateBook(id, updatedBookData);

            if(updatedBook == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(updatedBook);
        }catch (Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
        System.out.println("Delete called!");
        try{
            bookService.deleteBook(bookId);
            Map<String,String> response = new HashMap<>();
            response.put("message","Book deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to delete book.");
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(error);
        }
        
    }
/* 
    @GetMapping("/whoami")
    public String whoami() throws UnknownHostException {
        return "Book-service response from: " + instanceId;
    }

    @GetMapping("/self-test")
        public String selfLoadBalancedCall() {
        String response = restTemplate.getForObject("http://book-service/api/v1/book/whoami", String.class);
        return "Self-called: " + response;
    }
*/
}

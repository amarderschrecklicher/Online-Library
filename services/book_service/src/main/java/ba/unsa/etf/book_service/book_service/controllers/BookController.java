package ba.unsa.etf.book_service.book_service.controllers;

import ba.unsa.etf.book_service.book_service.dtos.BookDto;
import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.services.BookService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@CrossOrigin
class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.GetAllBooks();
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        System.out.println("Creating new Book!");

        if (bookService.GetBookByTitle(bookDto.getTitle())!=null) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
            .body("Book already exists");
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
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") Long id, @RequestBody Book updatedBookData){
        try{
            Book updatedBook = bookService.updateBook(id, updatedBookData);

            if(updatedBook == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(updatedBook);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        System.out.println("Delete called!");
        bookService.deleteBook(bookId);
    }

}

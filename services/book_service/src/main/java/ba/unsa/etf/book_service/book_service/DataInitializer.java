package ba.unsa.etf.book_service.book_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ba.unsa.etf.book_service.book_service.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            List<BookCopy> bookCopies1 = new ArrayList<>();
            List<BookCopy> bookCopies2 = new ArrayList<>();
            
            Book book1 = new Book(
                    null,
                    "1984",
                    "George Orwell",
                    "Dystopian novel about surveillance and totalitarianism.",
                    1949L,
                    bookCopies1
            );
            
            Book book2 = new Book(
                    null,
                    "To Kill a Mockingbird",
                    "Harper Lee",
                    "Classic novel about racial injustice in the Deep South.",
                    1960L,
                    bookCopies2
            );
            
            
            bookRepository.saveAll(List.of(book1, book2));

            System.out.println(bookRepository.findAll().get(1).getTitle());
        }
    }
}

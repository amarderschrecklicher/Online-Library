package ba.unsa.etf.book_service.book_service;

import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.models.BookCopy;
import ba.unsa.etf.book_service.book_service.repositories.BookCopyRepository;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class BookNPlusOneTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testBooksWithCopies_ShouldAvoidNPlusOne() {
        // 🔧 Insert test data manually
        for (int i = 1; i <= 3; i++) {
            Book book = Book.builder()
                    .title("Book " + i)
                    .author("Author " + i)
                    .genre("Genre " + i)
                    .publishedYear(2000L + i)
                    .build();

            book = bookRepository.save(book);

            // Two copies for each book
            for (int j = 1; j <= 2; j++) {
                BookCopy copy = BookCopy.builder()
                        .code("B" + i + "-C" + j)
                        .status("AVAILABLE")
                        .book(book)
                        .build();

                bookCopyRepository.save(copy);
            }
        }

        // ✅ Now clear the persistence context so Hibernate loads from DB
        entityManager.clear();

        // 🔍 Track SQL queries
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        // ✅ ACT: Fetch with JOIN FETCH
        List<Book> books = bookRepository.findAllWithCopies(); // This should have a JOIN FETCH

        // Force loading of book copies
        books.forEach(book -> book.getBookCopies().size());

        long queryCount = statistics.getPrepareStatementCount();
        System.out.println("Queries executed: " + queryCount);

        // ✅ ASSERT
        assertTrue(queryCount <= 2, "Expected max 2 queries, but got: " + queryCount);
    }
}
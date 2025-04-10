package ba.unsa.etf.book_service.book_service;

import ba.unsa.etf.book_service.book_service.models.Book;
import ba.unsa.etf.book_service.book_service.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class BookNPlusOneTest {

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testBooksWithCopies_ShouldAvoidNPlusOne() {
        // Clear persistence context to force fresh fetch
        entityManager.clear();

        // Get Hibernate session
        Session session = entityManager.unwrap(Session.class);
        Statistics statistics = session.getSessionFactory().getStatistics();
        statistics.clear();

        // ACT: Load books (with copies) – make sure fetch is done via join or entity graph
        List<Book> books = bookRepository.findAllWithCopies(); // use fetch join!

        // ACCESS copies (to simulate actual access and trigger lazy loading if it exists)
        books.forEach(book -> book.getBookCopies().size());

        long queryCount = statistics.getPrepareStatementCount();

        // ASSERT: Only 1 or 2 queries should be executed (not 1 per book)
        System.out.println("Queries executed: " + queryCount);
        assertTrue(queryCount <= 2, "Expected max 2 queries, but got: " + queryCount);
    }
}
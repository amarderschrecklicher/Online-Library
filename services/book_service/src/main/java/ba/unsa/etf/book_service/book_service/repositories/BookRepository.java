package ba.unsa.etf.book_service.book_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.book_service.book_service.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);
    boolean existsByTitle(String title);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.bookCopies")
    List<Book> findAllWithCopies();

}
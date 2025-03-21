package ba.unsa.etf.book_service.book_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.unsa.etf.book_service.book_service.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

   
}
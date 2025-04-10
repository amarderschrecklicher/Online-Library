package ba.unsa.etf.book_service.book_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.book_service.book_service.models.BookCopy;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    boolean existsByCode(String code);
    Optional<BookCopy> findById(Long id);
}
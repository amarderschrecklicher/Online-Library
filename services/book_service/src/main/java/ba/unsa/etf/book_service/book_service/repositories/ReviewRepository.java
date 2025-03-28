package ba.unsa.etf.book_service.book_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ba.unsa.etf.book_service.book_service.models.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

   
}
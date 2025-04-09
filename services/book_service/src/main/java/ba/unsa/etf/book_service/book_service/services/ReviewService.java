package ba.unsa.etf.book_service.book_service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.unsa.etf.book_service.book_service.dtos.ReviewDto;
import ba.unsa.etf.book_service.book_service.mappers.ReviewMapper;
import ba.unsa.etf.book_service.book_service.models.Review;
import ba.unsa.etf.book_service.book_service.repositories.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        if (reviewDto.getBookId() == null || reviewDto.getMemberId() == null) {
            throw new IllegalArgumentException("Book ID and Member ID are required.");
        }
    
        Review review = Review.builder()
                .bookId(reviewDto.getBookId())
                .memberId(reviewDto.getMemberId())
                .rating(reviewDto.getRating())
                .text(reviewDto.getText())
                .build();
    
        return ReviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewDto updateReview(Long id, ReviewDto updatedDto) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) return null;

        Review review = optionalReview.get();
        review.setBookId(updatedDto.getBookId());
        review.setMemberId(updatedDto.getMemberId());
        review.setRating(updatedDto.getRating());
        review.setText(updatedDto.getText());

        return ReviewMapper.toDto(reviewRepository.save(review));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Optional<ReviewDto> getReviewById(Long id) {
        return reviewRepository.findById(id).map(ReviewMapper::toDto);
    }    
}

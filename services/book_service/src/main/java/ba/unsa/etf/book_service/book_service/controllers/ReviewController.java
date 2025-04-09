package ba.unsa.etf.book_service.book_service.controllers;

import ba.unsa.etf.book_service.book_service.dtos.ReviewDto;
import ba.unsa.etf.book_service.book_service.services.ReviewService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto created = reviewService.createReview(reviewDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId") Long id, @Valid @RequestBody ReviewDto updatedDto) {
        ReviewDto updated = reviewService.updateReview(id, updatedDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long id) {
        reviewService.deleteReview(id);
    }    
}

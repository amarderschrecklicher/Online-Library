package ba.unsa.etf.book_service.book_service.mappers;

import ba.unsa.etf.book_service.book_service.dtos.ReviewDto;
import ba.unsa.etf.book_service.book_service.models.Review;

public class ReviewMapper {
    public static ReviewDto toDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setBookId(review.getBookId());
        dto.setMemberId(review.getMemberId());
        dto.setRating(review.getRating());
        dto.setText(review.getText());
        return dto;
    }    
}

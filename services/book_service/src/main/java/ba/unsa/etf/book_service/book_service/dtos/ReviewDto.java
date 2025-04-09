package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private Long bookId;
    private Long memberId;
    private Long rating;
    private String text;
}
package ba.unsa.etf.book_service.book_service.dtos;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ReviewDto {

    private Long id;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Long rating;

    @NotBlank(message = "Review text is required")
    @Size(max = 500, message = "Review text must not exceed 500 characters")
    private String text;
}
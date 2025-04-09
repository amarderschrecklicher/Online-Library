package ba.unsa.etf.book_service.book_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookCopyDto {
    private Long id;
    @NotNull(message = "Number is required")
    @Min(value = 1, message = "ISBN must be at least 1")
    private String isbn;
    @NotBlank(message = "Status is required")
    private String status;
    @NotNull(message = "Book ID is required")
    private Long bookId;
}


package ba.unsa.etf.membership_service.membership_service.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookCopyDto {
    private Long id;
    @Nullable
    private String code;
    @NotNull(message = "Availability is required")
    private Boolean available;
    @NotNull(message = "Book ID is required")
    private Long bookId;
}


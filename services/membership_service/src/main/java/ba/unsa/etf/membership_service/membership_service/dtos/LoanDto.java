package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class LoanDto {
    private Long id;

    @NotNull(message = "Member ID must not be null")
    private Long memberId;

    @NotNull(message = "Book copy ID must not be null")
    private Long bookCopyId;

    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;

    @NotNull(message = "Returned status must be set")
    private Boolean returned;
}

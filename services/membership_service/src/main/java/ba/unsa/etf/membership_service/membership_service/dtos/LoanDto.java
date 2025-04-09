package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoanDto {
    private Long id;
    private Long memberId;
    private Long bookCopyId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    private Boolean returned;
}


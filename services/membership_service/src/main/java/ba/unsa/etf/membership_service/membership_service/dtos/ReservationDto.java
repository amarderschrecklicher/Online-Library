package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;

    @NotNull(message = "Member ID must not be null")
    private Long memberId;

    @NotNull(message = "Book copy ID must not be null")
    private Long bookCopyId;

    private LocalDateTime reservationDate;

    @NotBlank(message = "Status must not be blank")
    private String status;
}

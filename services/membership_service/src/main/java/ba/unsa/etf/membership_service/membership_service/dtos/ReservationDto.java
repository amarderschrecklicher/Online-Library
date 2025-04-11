package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private Long memberId;
    private Long bookCopyId;
    private LocalDateTime reservationDate;
    private String status;
}

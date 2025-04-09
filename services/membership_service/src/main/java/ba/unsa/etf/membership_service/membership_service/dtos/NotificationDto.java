package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private Long memberId;
    private String message;
    private LocalDateTime sentAt;
    private String type;
}

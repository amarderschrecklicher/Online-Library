package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;

    @NotNull(message = "Member ID must not be null")
    private Long memberId;

    @NotBlank(message = "Message must not be blank")
    private String message;

    private LocalDateTime sentAt;

    @NotBlank(message = "Type must not be blank")
    private String type;
}

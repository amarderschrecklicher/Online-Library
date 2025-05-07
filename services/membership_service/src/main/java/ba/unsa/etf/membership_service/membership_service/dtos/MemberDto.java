package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private String status;
}

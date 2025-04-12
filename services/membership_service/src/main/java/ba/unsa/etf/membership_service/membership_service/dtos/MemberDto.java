package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String status;
}

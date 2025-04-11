package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String status;

    public MemberDto(String username, String email, String status) {
        this.username = username;
        this.email = email;
        this.status = status;
    }
}

package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}

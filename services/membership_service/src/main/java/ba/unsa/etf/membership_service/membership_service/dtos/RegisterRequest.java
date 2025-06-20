package ba.unsa.etf.membership_service.membership_service.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterRequest {

    @Schema(description = "Korisničko ime", example = "john_doe")
    private String username;

    @Schema(description = "Ime korisnika", example = "John")
    private String firstName;

    @Schema(description = "Prezime korisnika", example = "Doe")
    private String lastName;

    @Schema(description = "Email adresa", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Broj telefona", example = "+38760123456")
    private String phone;

    @Schema(description = "Adresa stanovanja", example = "Zmaja od Bosne bb")
    private String address;

    @Schema(description = "Lozinka", example = "password123")
    private String password;

    @Schema(description = "Potvrda lozinke", example = "password123")
    private String confirmPassword;
}

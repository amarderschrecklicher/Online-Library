package ba.unsa.etf.membership_service.membership_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Status must not be blank")
    private String status;

    public MemberDto(String username, String email, String status) {
        this.username = username;
        this.email = email;
        this.status = status;
    }
}

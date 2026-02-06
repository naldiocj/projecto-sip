package ao.gov.sic.sip.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "{login.email.required}")
    @Email(message = "{login.email.invalid}")
    private String email;

    @NotBlank(message = "{login.password.required}")
    private String password;
}

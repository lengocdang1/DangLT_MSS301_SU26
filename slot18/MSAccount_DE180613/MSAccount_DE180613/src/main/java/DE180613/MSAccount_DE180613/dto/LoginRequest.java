package DE180613.MSAccount_DE180613.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// [TODO-3.3: request body cho /api/auth/login]
@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}


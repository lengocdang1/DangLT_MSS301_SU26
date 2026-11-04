package DE180613.MSAccount_DE180613.dto;

import lombok.Builder;
import lombok.Data;

// [TODO-3.3: response body cho login/refresh]
@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer role;
    private String username;
}


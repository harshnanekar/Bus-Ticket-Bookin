package springs.dto;

import lombok.Data;

@Data
public class TokenResponse {
    public TokenResponse(String accessToken2, String refreshToken2) {
        //TODO Auto-generated constructor stub
    }
    private String accessToken;
    private String refreshToken;
}

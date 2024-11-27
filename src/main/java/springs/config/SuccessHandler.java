package springs.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private Token jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        final String accessToken = jwtUtil.generateToken(username);
        final String refreshToken = jwtUtil.generateRefreshToken(username);

        jwtUtil.setCookie(response, "accessToken", accessToken, 15 * 60);
        jwtUtil.setCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60);

        Map<String, String> json = new HashMap<>();
        json.put("status", "200");
        json.put("message", "Logged In Successfully!");

        new ObjectMapper().writeValue(response.getOutputStream(), json);
    }

}

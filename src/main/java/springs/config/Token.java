package springs.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Token {

    private String token;
    private SecretKey SECRET_KEY; 

    public Token(@Value("${SECRET_KEY}") String token) {
        this.token = token;
        this.SECRET_KEY = Keys.hmacShaKeyFor(token.getBytes());  
    }
    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        // return ((JwtParser) Jwts.parser().setSigningKey(SECRET_KEY)).parseClaimsJws(token).getBody();
        return Jwts.parserBuilder()  
        .setSigningKey(SECRET_KEY)
        .build()  
        .parseClaimsJws(token)
        .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    @SuppressWarnings("deprecation")
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    @SuppressWarnings("deprecation")
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days validity for refresh token
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public void setCookie(HttpServletResponse response,String cookieName,String cookieValue,int validity){
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setMaxAge(validity); 
        cookie.setPath("/");  
        cookie.setSecure(true); 
        cookie.setHttpOnly(true); 
        response.addCookie(cookie);
    }


     public String getTokenFromCookies(HttpServletRequest request,String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue(); 
                }
            }
        }
        return null;
    }

    public void deleteCookies(String cookieName,HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
    
}

package springs.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthMiddleware extends OncePerRequestFilter{

    private final static Logger logger = Logger.getLogger("Middleware");

    @Autowired
    private Token jwtUtil;

    @Autowired
    private UserData userData;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
    	
        logger.info("Inside middleware");
        String username = null;
        String jwt = null;


        jwt = jwtUtil.getTokenFromCookies(request, "accessToken");
        logger.info("access token " + jwt);
        if(jwt != null){
            username = jwtUtil.extractUsername(jwt);
        }else{
            handleInvalidTokenResponse(request,response, "Invalid or expired token");
            return;
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userData.loadUserByUsername(username);

            if (jwtUtil.isTokenExpired(jwt)) {
            	logger.info("Inside token expired");
               String refreshToken = jwtUtil.getTokenFromCookies(request, "refreshToken");
              
                if(refreshToken == null){
                    handleInvalidTokenResponse(request,response, "Invalid or expired token");
                    return;
                }           
        
                if(!jwtUtil.isTokenExpired(refreshToken) && jwtUtil.validateToken(refreshToken, userDetails.getUsername())) {
                    final String accessToken = jwtUtil.generateToken(username);        
                    jwtUtil.setCookie(response,"accessToken",accessToken,15 * 60 * 1000);
                }else{
                    handleInvalidTokenResponse(request,response, "Invalid or expired token");
                    return;
                }
            }else{
                if (!jwtUtil.validateToken(jwt, userDetails.getUsername())) { 
                	logger.info("To Check token validity");
                    handleInvalidTokenResponse(request,response, "Invalid or expired token");
                    return;
                }
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
          
        }
        filterChain.doFilter(request, response);
    }


     // Method to handle invalid or expired token response
    private void handleInvalidTokenResponse(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        if (isApiRequest(request,response)) {
            logger.info("invalid request json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", message); 

            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        } else { 
            logger.info("invalid request page");
            response.sendRedirect("/loginPage");
        }
    }

    //Method To Check Api Request Type
    private boolean isApiRequest(HttpServletRequest request,HttpServletResponse response) {
        String acceptHeader = request.getHeader("Accept");
        String contentTypeHeader = request.getHeader("Content-Type");

        boolean isJsonAcceptHeader = acceptHeader != null && acceptHeader.contains("application/json") ? true : false;
        if(acceptHeader == null) {
            response.setHeader("Content-Type", "text/html");
        }
        logger.info("accespt headers " + acceptHeader + contentTypeHeader);
        return isJsonAcceptHeader ;
    }


    //Method TO Avoid Filter For Particluar Paths
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        logger.info("check uri "+path);

        return path.equals("/loginPage") 
        || path.equals("/WEB-INF/jsp/login.jsp")
        || path.equals("/login") || path.equals("/register")
        || path.startsWith("/css/") || path.startsWith("/js/") 
        || path.startsWith("/resource/images/")
        || path.startsWith("/logout");
    }

   
    
}

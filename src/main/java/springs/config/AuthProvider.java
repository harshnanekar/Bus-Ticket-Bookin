package springs.config;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import springs.repository.UserDao;
import springs.service.LoginService;

@Component
public class AuthProvider implements AuthenticationProvider{

    private static final Logger logger = Logger.getLogger("authentication"); 
    
    @Autowired
    private UserData user; 
    
    @Autowired
    private BCryptPasswordEncoder encode;
    
    @Autowired
    private UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

       String username = authentication.getName();
       String password = authentication.getCredentials().toString();
       logger.info("authentication username " + authentication.getName());
       UserDetails userDetails = user.loadUserByUsername(username);
       if(userDetails != null){

        if(encode.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails, password, getAuthority(username));
        }else{
            throw new BadCredentialsException("Invalid Credentials");
        }

       }else{
        throw new UsernameNotFoundException("Username Not Found");
       }  
    }

    //Method To Set Authority For User
    private List<GrantedAuthority> getAuthority(String user) {
        List<Map<String,Object>> user_roles = userDao.getUserRoles(user);
        List<GrantedAuthority> authority =  user_roles.stream().
                                            map(ra -> new SimpleGrantedAuthority((String)ra.get("role"))).
                                            collect(Collectors.toList());

        logger.info("User roles auth provider " + authority.toString());

		return authority;
	}

    @Override
    public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}

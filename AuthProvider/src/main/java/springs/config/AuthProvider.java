package springs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import springs.dao.UserDao;

@Component
public class AuthProvider implements AuthenticationProvider{
	
	@Autowired
	private UserData user;
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private BCryptPasswordEncoder encode;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("inside authentication provider");
        UserDetails userdetails = user.loadUserByUsername(username);
        if(encode.matches(password, userdetails.getPassword())) {
    		return new UsernamePasswordAuthenticationToken(username,userdetails.getPassword(),getAuthority(username));
        }else {
        	throw new BadCredentialsException("Invalid Credentials");
        }
	}
	
	
	private List<GrantedAuthority> getAuthority(String user) {
		List<Map<String,Object>> auth = dao.getUsername(user);
		List<GrantedAuthority> li = new ArrayList<>();
		
		String role =(String) auth.get(0).get("roles");
		li.add(new SimpleGrantedAuthority(role));
		
		return li;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
}

package springs.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import springs.dao.UserDao;
import springs.model.UserDto;

@Component
public class UserData implements UserDetailsService {

	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<Map<String,Object>> user = userDao.getUsername(username);
		List<GrantedAuthority> authority = new ArrayList<>();
		System.out.println("User details " + user.toString());
		String users;
		String pass;
		if(user.size() == 0) {
			throw new UsernameNotFoundException("Username Not found");
		}else {
			users = (String) user.get(0).get("email");
			pass = (String) user.get(0).get("password");
			String auth = (String) user.get(0).get("roles");
			authority.add(new SimpleGrantedAuthority(auth));
		}
		return new User(users,pass,authority);
	}

}

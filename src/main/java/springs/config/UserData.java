package springs.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import springs.repository.UserDao;

@Component
@AllArgsConstructor
public class UserData implements UserDetailsService{

    private static final Logger logger = Logger.getLogger("UserData");
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Map<String,Object> userDetails = userDao.getUserByUsername(username);
       logger.info("user details " + userDetails.toString());

       if(userDetails.size() > 0) {
        String password = (String)userDetails.get("password");

        List<Map<String,Object>> user_roles = userDao.getUserRoles(username);
        List<GrantedAuthority> authority =  user_roles.stream().
                                            map(ra -> new SimpleGrantedAuthority((String)ra.get("role"))).
                                            collect(Collectors.toList());

        logger.info("User roles " + authority.toString());
        return new User(username, password ,authority);
       }else{
        throw new UsernameNotFoundException("Username Not found");
       }
    }
    
}

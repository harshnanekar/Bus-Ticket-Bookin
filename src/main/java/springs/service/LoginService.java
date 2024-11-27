package springs.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import springs.config.Token;
import springs.repository.UserDao;

@Service
public class LoginService {

  @Autowired
  private Token token;

  @Autowired
  private UserDao dao;

  @Autowired
  private HttpSession session;

  public void logoutService() {
    token.deleteCookies("accessToken", null);
    token.deleteCookies("refreshToken", null);
  }

  // Service To Get Dashboard Page
  public String getDashboardService() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    
    List<GrantedAuthority> roles = (List<GrantedAuthority>) auth.getAuthorities();
    List<String> roleNames = roles.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    List<Map<String, Object>> modules = dao.getDashboardModules(name);   
    session.setAttribute("modules", modules); 
    session.setAttribute("username", name);

    Map<String, Object> getUserRole = dao.getUserRoleType(name, roleNames);
    String page = "redirect:/";
    System.out.println("user role for page " + getUserRole.toString());

    if (getUserRole.get("role_type").equals("admin")) {
      page = page + "admin-dashboard";
    } else {
      page = page + "user-dashboard";
    }

    System.out.println("user logged in details " + name);
    return page;
  }


}

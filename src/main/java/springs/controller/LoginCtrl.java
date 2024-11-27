package springs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import springs.service.LoginService;

@Controller
public class LoginCtrl {

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpSession session;

    // Method To Return Login Page
    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    // Method To Return Login Failed JSON
    @GetMapping("/login-failed")
    public ResponseEntity<Object> loginFailed() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login Failed");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Method To Redirect Dashboard Page
    @GetMapping("/dashboard")
    public String dashboard() {
        String dashboard = loginService.getDashboardService();
        return dashboard;
    }

    // Method To Return Admin Dashboard Page
    @GetMapping("/admin-dashboard")
    @Secured("ROLE_ADMIN")
    public String adminDashboard(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        System.out.println("Modules data: " + modules);
        m.addAttribute("modules", modules);
        return "admin-dashboard";
    }

    // Method To Return User Dahsboard Page
    @GetMapping("/user-dashboard")
    @Secured("ROLE_USER")
    public String userDashboard(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "user-dashboard";
    }

    @GetMapping("/test")
    public String test() {
        return "error/access-denied";
    }
}

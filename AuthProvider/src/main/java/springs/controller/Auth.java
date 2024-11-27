package springs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Auth {

	@GetMapping("/login-success")
	public ResponseEntity<String> loginSuccess() {
		System.out.println("Inside login success");
	    String message = "Login Success!";
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/login-success-admin")
	public ResponseEntity<String> loginSuccessAdmin() {
		System.out.println("Inside login success");
	    String message = "Login Success Admin!";
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	
	@GetMapping("/expired-session")
	public ResponseEntity<String> expiredSession() {
		System.out.println("Inside login success");
	    String message = "Session Expired!";
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping("/login-failed")
	public ResponseEntity<String> loginFailed() {
		System.out.println("Inside login failed");
	    String message = "Login Failed!";
	    return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
	}

}

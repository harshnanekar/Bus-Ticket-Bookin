package springs.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest request, Exception e) {

        String acceptHeader = request.getHeader("Accept");

        if (acceptHeader.contains("text/html")) {
            return new ModelAndView("error/error");
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", status.value());
            responseBody.put("message", e.getMessage());

            return ResponseEntity
                    .status(status)
                    .body(responseBody);
        }
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public Object handleAccessDenied(HttpServletRequest request, AccessDeniedException e) {
        String acceptHeader = request.getHeader("Accept");

        if (acceptHeader != null && acceptHeader.contains("text/html")) {
            return new ModelAndView("redirect:/test");
        } else {
            HttpStatus status = HttpStatus.FORBIDDEN;

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", status.value());
            responseBody.put("message", e.getMessage());

            return ResponseEntity
                    .status(status)
                    .body(responseBody);
        }
    }

    @ExceptionHandler(DbExceptionHandler.class)
    public ResponseEntity<Object> handleDbException(HttpServletResponse response, DbExceptionHandler e) {
        HttpStatus status = HttpStatus.NOT_IMPLEMENTED;

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", status.value());
        responseBody.put("message", e.getMessage());

        return ResponseEntity.status(status).body(responseBody);
    }

}

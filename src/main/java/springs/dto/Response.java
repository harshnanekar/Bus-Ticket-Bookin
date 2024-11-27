package springs.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Response {
    private int status;
    private String message;
}

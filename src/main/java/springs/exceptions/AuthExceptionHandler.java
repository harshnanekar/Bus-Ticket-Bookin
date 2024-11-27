package springs.exceptions;


public class AuthExceptionHandler extends RuntimeException{


    private int status; 
    private String message;

    public AuthExceptionHandler(int status,String message){
        super(message);
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

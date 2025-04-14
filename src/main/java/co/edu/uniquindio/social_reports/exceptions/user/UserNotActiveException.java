package co.edu.uniquindio.social_reports.exceptions.user;

public class UserNotActiveException extends RuntimeException{
    public UserNotActiveException(String message) {
        super(message);
    }
}

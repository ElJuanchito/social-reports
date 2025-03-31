package co.edu.uniquindio.social_reports.exceptions.user;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message) {
        super(message);
    }
}

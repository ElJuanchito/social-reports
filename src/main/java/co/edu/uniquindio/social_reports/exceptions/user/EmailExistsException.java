package co.edu.uniquindio.social_reports.exceptions.user;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}

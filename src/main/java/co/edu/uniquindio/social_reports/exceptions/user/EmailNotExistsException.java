package co.edu.uniquindio.social_reports.exceptions.user;

public class EmailNotExistsException extends RuntimeException {
    public EmailNotExistsException(String message) {
        super(message);
    }
}

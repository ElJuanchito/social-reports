package co.edu.uniquindio.social_reports.exceptions.user;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message) {
        super(message);
    }
}

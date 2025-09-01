package co.com.auth.usecase.exeption;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

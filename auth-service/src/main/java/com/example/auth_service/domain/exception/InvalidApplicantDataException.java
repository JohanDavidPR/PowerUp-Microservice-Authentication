package com.example.auth_service.domain.exception;

public class InvalidApplicantDataException extends RuntimeException {
    public InvalidApplicantDataException(String message) {
        super(message);
    }
}

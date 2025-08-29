package co.com.auth.usecase.registerapplicant.utils;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.usecase.registerapplicant.exception.ValidationException;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

public class ApplicantValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);

    public Mono<Applicant> validate(Applicant applicant) {
        if (isBlank(applicant.getFirstName())) {
            return Mono.error(new ValidationException("First name is required"));
        }
        if (isBlank(applicant.getLastName())) {
            return Mono.error(new ValidationException("Last name is required"));
        }
        if (isBlank(applicant.getEmail())) {
            return Mono.error(new ValidationException("Email is required"));
        }
        if (!EMAIL_PATTERN.matcher(applicant.getEmail()).matches()) {
            return Mono.error(new ValidationException("Invalid email format"));
        }
        if (applicant.getBaseSalary() == null) {
            return Mono.error(new ValidationException("Base salary is required"));
        }
        if (applicant.getBaseSalary().compareTo(Constants.MIN_SALARY) < 0 || applicant.getBaseSalary().compareTo(Constants.MAX_SALARY) > 0) {
            return Mono.error(new ValidationException("Base salary must be between 0 and 15,000,000"));
        }

        return Mono.just(applicant);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
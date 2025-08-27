package co.com.auth.usecase.registerapplicant;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegisterApplicantUseCase {

    private final ApplicantRepository applicantRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final BigDecimal MIN_SALARY = BigDecimal.ZERO;
    private static final BigDecimal MAX_SALARY = new BigDecimal("15000000");

    public Mono<Applicant> register(Applicant applicant) {
        return validateApplicant(applicant)
                .flatMap(valid -> applicantRepository.existsByEmail(applicant.getEmail()))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("Email already exists"));
                    }
                    return applicantRepository.save(applicant);
                });
    }

    private Mono<Boolean> validateApplicant(Applicant applicant) {
        if (isBlank(applicant.getFirstName())) {
            return Mono.error(new IllegalArgumentException("First name is required"));
        }
        if (isBlank(applicant.getLastName())) {
            return Mono.error(new IllegalArgumentException("Last name is required"));
        }
        if (isBlank(applicant.getEmail())) {
            return Mono.error(new IllegalArgumentException("Email is required"));
        }
        if (!EMAIL_PATTERN.matcher(applicant.getEmail()).matches()) {
            return Mono.error(new IllegalArgumentException("Invalid email format"));
        }

        BigDecimal salary = applicant.getBaseSalary();

        if (salary == null) {
            return Mono.error(new IllegalArgumentException("Base salary is required"));
        }
        if (salary.compareTo(MIN_SALARY) < 0  || salary.compareTo(MAX_SALARY) > 0) {
            return Mono.error(new IllegalArgumentException("Base salary must be between 0 and 15,000,000"));
        }

        return Mono.just(true);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}

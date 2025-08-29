package co.com.auth.usecase.registerapplicant;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import co.com.auth.usecase.registerapplicant.exception.DuplicateEmailException;
import co.com.auth.usecase.registerapplicant.utils.ApplicantValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegisterApplicantUseCase {

    private final ApplicantRepository applicantRepository;
    private final ApplicantValidator applicantValidator;

    public Flux<Applicant> getAllApplicants() {
        return applicantRepository.getAllApplicants();
    }

    public Mono<Applicant> register(Applicant applicant) {
        return applicantValidator.validate(applicant)
                .flatMap(this::ensureEmailNotExists)
                .flatMap(applicantRepository::save);
    }

    public Mono<Applicant> ensureEmailNotExists(Applicant applicant) {
        return applicantRepository.existsByEmail(applicant.getEmail())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DuplicateEmailException(applicant.getEmail())))
                .thenReturn(applicant);
    }

}

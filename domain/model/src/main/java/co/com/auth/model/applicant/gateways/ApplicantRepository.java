package co.com.auth.model.applicant.gateways;

import co.com.auth.model.applicant.Applicant;
import reactor.core.publisher.Mono;

public interface ApplicantRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<Applicant> save(Applicant applicant);
}

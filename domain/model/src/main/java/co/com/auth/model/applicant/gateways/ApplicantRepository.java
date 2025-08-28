package co.com.auth.model.applicant.gateways;

import co.com.auth.model.applicant.Applicant;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApplicantRepository {
    Mono<Boolean> existsByEmail(String email);
    Flux<Applicant> getAllApplicants();
    Mono<Applicant> save(Applicant applicant);
}

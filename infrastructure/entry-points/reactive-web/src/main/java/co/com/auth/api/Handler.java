package co.com.auth.api;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.usecase.registerapplicant.RegisterApplicantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterApplicantUseCase registerApplicantUseCase;

    public Mono<ServerResponse> registerApplicant(ServerRequest request) {
        return request.bodyToMono(Applicant.class)
                .flatMap(registerApplicantUseCase::register)
                .flatMap(applicant ->
                        ServerResponse.created(null)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(applicant))
                .onErrorResume(e ->
                        ServerResponse.badRequest()
                                .bodyValue(e.getMessage()));
    }
}

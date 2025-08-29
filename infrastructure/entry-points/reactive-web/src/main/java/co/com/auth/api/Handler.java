package co.com.auth.api;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.usecase.registerapplicant.RegisterApplicantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterApplicantUseCase registerApplicantUseCase;

    public Mono<ServerResponse> getAllApplicants(ServerRequest request) {
        return registerApplicantUseCase.getAllApplicants().collectList().flatMap(applicants ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(applicants)
        );
    }

    public Mono<ServerResponse> registerApplicant(ServerRequest request) {
        return request.bodyToMono(Applicant.class)
                .flatMap(registerApplicantUseCase::register)
                .flatMap(applicant ->
                        ServerResponse.created(null)
                                .build())
                .onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(Map.of("error", ex.getMessage()))
                );
    }
}

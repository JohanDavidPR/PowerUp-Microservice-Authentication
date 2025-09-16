package co.com.auth.api;

import co.com.auth.api.dto.LoginRequest;
import co.com.auth.api.security.JwtProvider;
import co.com.auth.model.applicant.Applicant;
import co.com.auth.usecase.exeption.UnauthorizedException;
import co.com.auth.usecase.login.LoginUseCase;
import co.com.auth.usecase.loginattempt.LoginAttemptUseCase;
import co.com.auth.usecase.registerapplicant.RegisterApplicantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterApplicantUseCase registerApplicantUseCase;
    private final LoginAttemptUseCase loginAttemptUseCase;
    private final LoginUseCase loginUseCase;
    private final JwtProvider jwtProvider;

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

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(body -> loginUseCase.login(body.getEmail(), body.getPassword()))
                .map(user -> {
                    String token = jwtProvider.generateToken(user.getEmail(), user.getRol().getName());
                    return Map.of("token", token);
                })
                .flatMap(tokenMap -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(tokenMap))
                .onErrorResume(UnauthorizedException.class, ex ->
                        ServerResponse.status(401).bodyValue(Map.of("error", ex.getMessage()))
                );
    }
}

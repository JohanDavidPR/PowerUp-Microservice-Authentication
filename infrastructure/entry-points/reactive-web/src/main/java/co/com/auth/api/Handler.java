package co.com.auth.api;

import co.com.auth.api.dto.LoginRequest;
import co.com.auth.api.dto.LoginResponse;
import co.com.auth.api.dto.RegisterUserDto;
import co.com.auth.api.security.JwtProvider;
import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.user.User;
import co.com.auth.usecase.exeption.UnauthorizedException;
import co.com.auth.usecase.login.LoginUseCase;
import co.com.auth.usecase.loginattempt.LoginAttemptUseCase;
import co.com.auth.usecase.registerapplicant.RegisterApplicantUseCase;
import co.com.auth.usecase.registeruser.RegisterUserUseCase;
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
    private final RegisterUserUseCase registerUserUseCase;
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
                .flatMap(body -> loginAttemptUseCase.canLogin(body.getEmail()))
                .filter(canLogin -> canLogin)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User is blocked due to too many failed login attempts")))
                .flatMap(canLogin -> request.bodyToMono(LoginRequest.class))
                .flatMap(body -> loginUseCase.login(body.getEmail(), body.getPassword()))
                .flatMap(applicant -> {
                    String token = jwtProvider.generateToken(applicant.getId(), applicant.getEmail());
                    LoginResponse response = new LoginResponse();
                    response.setToken(token);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .onErrorResume(UnauthorizedException.class, ex ->
                        ServerResponse.status(401).bodyValue(Map.of("error", ex.getMessage()))
                )
                .onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(Map.of("error", ex.getMessage()))
                );
    }

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(RegisterUserDto.class)
                .map(dto -> new User(
                        null,
                        dto.getEmail(),
                        dto.getPassword(),
                        dto.getRol(),
                        0
                ))
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(applicant ->
                        ServerResponse.created(null)
                                .build())
                .onErrorResume(IllegalArgumentException.class, ex ->
                        ServerResponse.badRequest().bodyValue(Map.of("error", ex.getMessage()))
                );
    }
}

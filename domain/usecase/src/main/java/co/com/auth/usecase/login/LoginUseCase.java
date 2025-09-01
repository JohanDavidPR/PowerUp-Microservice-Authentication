package co.com.auth.usecase.login;

import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.usecase.exeption.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;

    public Mono<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("Invalid credentials")))
                .flatMap(u -> encoder.matches(rawPassword, u.getPasswordHash())
                        ? Mono.just(u)
                        : Mono.error(new UnauthorizedException("Invalid credentials")))
                .map(tokenService::createToken);
    }

}

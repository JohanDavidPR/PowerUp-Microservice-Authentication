package co.com.auth.usecase.loginattempt;

import co.com.auth.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginAttemptUseCase {
    private final UserRepository userRepository;
    private final int maxAttempts = 5;

    public Mono<Void> incrementLoginAttempts(String email) {
        return userRepository.incrementAttempts(email).then();
    }

    public Mono<Boolean> canLogin(String email) {
        return userRepository.getAttempts(email)
                .map(attempts -> attempts < maxAttempts);
    }
}

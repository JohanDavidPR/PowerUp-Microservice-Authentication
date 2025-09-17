package co.com.auth.usecase.registeruser;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public Mono<Void> registerUser(User user) {
        return userRepository.save(user).then();
    }
}

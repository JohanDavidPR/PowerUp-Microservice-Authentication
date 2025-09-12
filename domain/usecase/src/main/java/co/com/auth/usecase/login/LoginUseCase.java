package co.com.auth.usecase.login;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.usecase.exeption.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public Mono<User> login(String email, String password){
        return userRepository.findByEmail(email)
                .filter(user -> encoder.matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new UnauthorizedException("Invalid credentials")))
                .map(user -> user.toBuilder().password(null).build());
    }

}

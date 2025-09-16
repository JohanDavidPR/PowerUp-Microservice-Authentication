package co.com.auth.model.user.gateways;

import co.com.auth.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findByEmail(String email);
    Mono<Integer> getAttempts(String email);
    Mono<Void> incrementAttempts(String email);
    Mono<Boolean> validatePassword(String password, String encodedPassword);
}

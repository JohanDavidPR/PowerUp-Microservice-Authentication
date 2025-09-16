package co.com.auth.usecase.login;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UserRepository userRepository;

    public Mono<User> login(String email, String password){
        return userRepository.findByEmail(email)
                .flatMap(user ->
                        userRepository.validatePassword(password, user.getPassword())
                                .flatMap(valid -> valid
                                        ? Mono.just(user)
                                        : Mono.error(new RuntimeException("Credenciales inválidas"))
                                )
                )
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }

}

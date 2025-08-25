package com.example.auth_service.application.service;

import com.example.auth_service.application.port.in.RegisterUserUseCase;
import com.example.auth_service.application.port.out.UserRepositoryPort;
import com.example.auth_service.domain.exception.EmailAlreadyExistsException;
import com.example.auth_service.domain.model.User;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;

public class UserService implements RegisterUserUseCase {

    private final UserRepositoryPort repository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // --> Nota: Recordar mover a archivo de constantes
    private static final String USER_REGISTRATION_LOG = "Registering user with email: {}";
    private static final String USER_REGISTERED_LOG = "User registered successfully with ID: {}";

    public UserService(UserRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Mono<User> register(User user) {
        return validateUser(user)
                .then(checkUserExists(user.getEmail()))
                .then(repository.save(user))
                .doOnSubscribe(s -> log.info(USER_REGISTRATION_LOG, user.getEmail()))
                .doOnSuccess(saved -> log.info(USER_REGISTERED_LOG, saved.getId()))
                .doOnError(error -> log.error("Error registering user: {}", error.getMessage()));
    }

    private Mono<Void> checkUserExists(String email) {
        return repository.findByEmail(email)
                .flatMap(existing -> Mono.error(new EmailAlreadyExistsException("Email already registered")))
                .then(); // convertimos a Mono<Void>
    }

    private Mono<Void> validateUser(User user) {
        return Mono.fromRunnable(() -> {
            if (user.getFirstName() == null || user.getFirstName().isBlank())
                throw new IllegalArgumentException("firstName cannot be null or empty");
            if (user.getLastName() == null || user.getLastName().isBlank())
                throw new IllegalArgumentException("lastName cannot be null or empty");
            if (user.getEmail() == null || user.getEmail().isBlank())
                throw new IllegalArgumentException("email cannot be null or empty");
            if (!EMAIL_PATTERN.matcher(user.getEmail()).matches())
                throw new IllegalArgumentException("Invalid email format");
            if (user.getBaseSalary() < 0 || user.getBaseSalary() > 15000000)
                throw new IllegalArgumentException("baseSalary must be between 0 and 15000000");
        });
    }
}

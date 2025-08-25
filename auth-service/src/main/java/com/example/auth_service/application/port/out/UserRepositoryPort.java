package com.example.auth_service.application.port.out;

import com.example.auth_service.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<User> findByEmail(String email);
    Mono<User> save(User applicant);
}

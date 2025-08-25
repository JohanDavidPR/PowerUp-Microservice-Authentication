package com.example.auth_service.application.port.in;

import com.example.auth_service.domain.model.User;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<User> register(User applicant);
}

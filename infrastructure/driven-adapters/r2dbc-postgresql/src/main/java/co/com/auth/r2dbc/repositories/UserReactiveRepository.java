package co.com.auth.r2dbc.repositories;

import co.com.auth.model.user.User;
import co.com.auth.r2dbc.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<User> login(String email, String password);
    Mono<User> findByEmail(String email);
    Mono<Integer> getAttempts(String email);
    Mono<Void> incrementAttempts(String email);
}

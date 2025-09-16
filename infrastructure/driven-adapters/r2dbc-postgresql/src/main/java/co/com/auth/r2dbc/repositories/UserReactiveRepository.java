package co.com.auth.r2dbc.repositories;

import co.com.auth.model.user.User;
import co.com.auth.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<User> findByEmail(String email);
    @Query("SELECT attempts FROM user WHERE email = :email")
    Mono<Integer> getAttempts(String email);
    @Query("UPDATE user SET attempts = attempts + 1 WHERE email = :email")
    Mono<Void> incrementAttempts(String email);
}

package co.com.auth.r2dbc;

import co.com.auth.r2dbc.entity.ApplicantEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository extends ReactiveCrudRepository<ApplicantEntity, String>, ReactiveQueryByExampleExecutor<ApplicantEntity> {
    Mono<Boolean> existsByEmail(String email);
}

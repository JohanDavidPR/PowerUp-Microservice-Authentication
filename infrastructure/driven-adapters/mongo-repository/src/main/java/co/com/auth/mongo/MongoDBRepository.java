package co.com.auth.mongo;

import co.com.auth.model.applicant.Applicant;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MongoDBRepository extends ReactiveMongoRepository<Applicant, String>, ReactiveQueryByExampleExecutor<Applicant> {
    Mono<Boolean> existsByEmail(String email);
}

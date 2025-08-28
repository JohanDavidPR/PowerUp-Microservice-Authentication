package co.com.auth.mongo;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.mongo.entity.ApplicantEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MongoDBRepository extends ReactiveMongoRepository<ApplicantEntity, String>, ReactiveQueryByExampleExecutor<ApplicantEntity> {
    Mono<Boolean> existsByEmail(String email);
}

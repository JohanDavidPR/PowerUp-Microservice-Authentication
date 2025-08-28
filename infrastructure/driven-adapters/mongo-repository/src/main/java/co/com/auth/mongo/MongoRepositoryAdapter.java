package co.com.auth.mongo;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import co.com.auth.mongo.entity.ApplicantEntity;
import co.com.auth.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<Applicant, ApplicantEntity, String, MongoDBRepository> implements ApplicantRepository
{
    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Applicant.class));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Flux<Applicant> getAllApplicants() {
        return repository.findAll().map(entity -> mapper.map(entity, Applicant.class));
    }

}

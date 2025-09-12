package co.com.auth.r2dbc;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import co.com.auth.r2dbc.entity.ApplicantEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.MyReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<Applicant, ApplicantEntity, String, MyReactiveRepository> implements ApplicantRepository {

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
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

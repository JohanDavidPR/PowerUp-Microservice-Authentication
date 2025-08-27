package co.com.auth.mongo;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import co.com.auth.mongo.entity.ApplicantEntity;
import co.com.auth.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<Applicant, Applicant, String, MongoDBRepository> implements ApplicantRepository
{
    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Applicant.class));
        this.repository = repository;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    /*@Override
    public Mono<Applicant> save(Applicant applicant) {
        return repository.save(toEntity(applicant)).map(this::toModel);
    }

    private ApplicantEntity toEntity(Applicant model) {
        ApplicantEntity entity = new ApplicantEntity();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setBirthDate(model.getBirthDate());
        entity.setAddress(model.getAddress());
        entity.setPhone(model.getPhone());
        entity.setEmail(model.getEmail());
        entity.setBaseSalary(model.getBaseSalary().doubleValue());
        return entity;
    }

    private Applicant toModel(ApplicantEntity entity) {
        return Applicant.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .baseSalary(new BigDecimal(entity.getBaseSalary()))
                .build();
    }*/

}

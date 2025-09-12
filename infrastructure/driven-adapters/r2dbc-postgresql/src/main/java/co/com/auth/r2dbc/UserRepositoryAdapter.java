package co.com.auth.r2dbc;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.r2dbc.entity.UserEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.UserReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<Integer> getAttempts(String email) {
        return repository.getAttempts(email);
    }

    @Override
    public Mono<Void> incrementAttempts(String email) {
        return repository.incrementAttempts(email).then();
    }
}

package co.com.auth.r2dbc;

import co.com.auth.model.user.User;
import co.com.auth.model.user.gateways.UserRepository;
import co.com.auth.r2dbc.entity.UserEntity;
import co.com.auth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.auth.r2dbc.repositories.UserReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {

    private final PasswordEncoder passwordEncoder;

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public Mono<Boolean> validatePassword(String password, String encodedPassword) {
        return Mono.fromCallable(() -> passwordEncoder.matches(password, encodedPassword));
    }

    @Override
    public Mono<User> save(User user) {
        User userToSave = user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return super.save(userToSave);
    }

}

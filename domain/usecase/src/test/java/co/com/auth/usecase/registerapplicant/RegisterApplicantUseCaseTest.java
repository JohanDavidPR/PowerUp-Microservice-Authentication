package co.com.auth.usecase.registerapplicant;

import co.com.auth.model.applicant.Applicant;
import co.com.auth.model.applicant.gateways.ApplicantRepository;
import co.com.auth.usecase.registerapplicant.exception.DuplicateEmailException;
import co.com.auth.usecase.registerapplicant.exception.ValidationException;
import co.com.auth.usecase.registerapplicant.utils.ApplicantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

class RegisterApplicantUseCaseTest {

    private ApplicantRepository applicantRepository;
    private ApplicantValidator applicantValidator;
    private RegisterApplicantUseCase useCase;

    @BeforeEach
    void setUp() {
        applicantRepository = Mockito.mock(ApplicantRepository.class);
        applicantValidator = new ApplicantValidator();
        useCase = new RegisterApplicantUseCase(applicantRepository, applicantValidator);

        Mockito.when(applicantRepository.existsByEmail(Mockito.anyString())).thenReturn(Mono.just(false));
    }

    @Test
    void shouldRegisterApplicantSuccessfully() {
        Applicant applicant = new Applicant("1", "John", "Doe",
                LocalDate.of(2002, 1, 1), "Address", "555-1234",
                "john@email.com", BigDecimal.valueOf(1000));

        Mockito.when(applicantRepository.existsByEmail(applicant.getEmail())).thenReturn(Mono.just(false));
        Mockito.when(applicantRepository.save(applicant)).thenReturn(Mono.just(applicant));

        StepVerifier.create(useCase.register(applicant))
                .expectNext(applicant)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenEmailExists() {
        Applicant applicant = Applicant.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .baseSalary(BigDecimal.valueOf(1000))
                .build();

        Mockito.when(applicantRepository.existsByEmail(applicant.getEmail())).thenReturn(Mono.just(true));

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof DuplicateEmailException &&
                        e.getMessage().contains(applicant.getEmail()))
                .verify();
    }

    @Test
    void shouldFailWhenFirstNameIsMissing() {
        Applicant applicant = Applicant.builder()
                .lastName("Doe")
                .email("john@email.com")
                .baseSalary(BigDecimal.valueOf(1000))
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException &&
                        e.getMessage().equals("First name is required"))
                .verify();
    }

    @Test
    void shouldFailWhenLastNameIsMissing() {
        Applicant applicant = Applicant.builder()
                .firstName("John")
                .email("john@email.com")
                .baseSalary(BigDecimal.valueOf(1000))
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException &&
                        e.getMessage().equals("Last name is required"))
                .verify();
    }

    @Test
    void shouldFailWhenEmailIsMissing() {
        Applicant applicant = Applicant.builder()
                .firstName("John")
                .lastName("Doe")
                .baseSalary(BigDecimal.valueOf(1000))
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException &&
                        e.getMessage().equals("Email is required"))
                .verify();
    }

    @Test
    void shouldFailWhenBaseSalaryIsMissing() {
        Applicant applicant = Applicant.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException &&
                        e.getMessage().equals("Base salary is required"))
                .verify();
    }

    @Test
    void shouldFailWhenEmailFormatIsInvalid() {
        Applicant applicant = Applicant.builder()
                .firstName("John")
                .lastName("Doe")
                .email("bad-email")
                .baseSalary(BigDecimal.valueOf(1000))
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException &&
                        e.getMessage().equals("Invalid email format"))
                .verify();
    }

    @Test
    void shouldFailWhenSalaryIsOutOfRange() {
        Applicant applicant = Applicant.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .baseSalary(BigDecimal.valueOf(20000000))
                .build();

        StepVerifier.create(useCase.register(applicant))
                .expectErrorMatches(e -> e instanceof ValidationException && e.getMessage().equals("Base salary must be between 0 and 15,000,000"))
                .verify();
    }
}
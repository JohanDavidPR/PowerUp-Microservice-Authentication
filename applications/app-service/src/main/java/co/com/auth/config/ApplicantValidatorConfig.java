package co.com.auth.config;

import co.com.auth.usecase.utils.ApplicantValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicantValidatorConfig {

    @Bean
    public ApplicantValidator applicantValidator() {
        return new ApplicantValidator();
    }
}

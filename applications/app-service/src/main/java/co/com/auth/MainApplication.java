package co.com.auth;

import co.com.auth.api.logging.ReactiveLogger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication {
    public static void main(String[] args) {
        ReactiveLogger.enable(LoggerFactory.getLogger("reactive-trace"));
        SpringApplication.run(MainApplication.class, args);
    }
}

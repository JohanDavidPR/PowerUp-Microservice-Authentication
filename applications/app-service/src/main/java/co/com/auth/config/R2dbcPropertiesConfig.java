package co.com.auth.config;

import co.com.auth.r2dbc.config.PostgresqlConnectionProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PostgresqlConnectionProperties.class)
public class R2dbcPropertiesConfig {
}
package com.whispr.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MigrationConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.flyway.clean-disabled", havingValue = "false")
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            flyway.clean();     // Clean the database before migration
            flyway.migrate();
        };
    }
}

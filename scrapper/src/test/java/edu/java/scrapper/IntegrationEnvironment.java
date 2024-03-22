package edu.java.scrapper;

import java.nio.file.Path;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.SneakyThrows;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationEnvironment {
    public static PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        POSTGRES.start();

        runMigrations(POSTGRES);
    }

    @SneakyThrows
    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        Database database = DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(new JdbcConnection(c.createConnection("")));

        Path directory = Path.of("src", "main", "resources", "migrations");
        var resourceAccessor = new DirectoryResourceAccessor(directory);
        Liquibase liquibase = new Liquibase("master.xml", resourceAccessor, database);
        liquibase.update();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver", POSTGRES::getDriverClassName);

        registry.add("database.postgresql.url", POSTGRES::getJdbcUrl);
        registry.add("database.postgresql.username", POSTGRES::getUsername);
        registry.add("database.postgresql.password", POSTGRES::getPassword);
        registry.add("database.postgresql.driver", POSTGRES::getDriverClassName);

    }
}

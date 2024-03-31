package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.ClientsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({ApplicationConfig.class, ClientsConfigurationProperties.class})
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}

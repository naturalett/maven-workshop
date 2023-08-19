package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
@RestController
public class HelloWorldApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ConditionalOnProperty(name = "app.init-db", havingValue = "true", matchIfMissing = true)
    @Bean
    public void init() {
        // Create the 'healthcheck' table if it doesn't exist
        jdbcTemplate.update(
                "CREATE TABLE IF NOT EXISTS healthcheck (id INT AUTO_INCREMENT PRIMARY KEY, date TIMESTAMP);"
        );
    }

    @GetMapping("/")
    public String hello() {
        String text = System.getenv("text");
        if (text == null || text.isEmpty()) {
            text = "Hello, World!";
        }
        return text;
    }

    @GetMapping("/healthcheck")
    public String healthCheck() {
        Date currentDate = new Date();
        jdbcTemplate.update("INSERT INTO healthcheck (date) VALUES (?)", new Timestamp(currentDate.getTime()));
        return "Health check passed!";
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}

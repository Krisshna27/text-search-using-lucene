package com.solution.textsearch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to handle OpenAPI meta-info.
 *
 * @author Krisshna Kumar Mone
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApiCustomConfig() {
        final var apiTitle = "Number Search API";
        final var apiDescription = "<br>Search API to find numbers within uploaded files or request contents<br>";
        return new OpenAPI()
                .info(new Info().title(apiTitle)
                        .description(apiDescription).version("v1.0")
                .contact(new Contact().name("Krisshna Kumar Mone")
                .email("krisshnakumar.m07@gmail.com")
                .url("https://www.linkedin.com/in/krisshna-kumar-mone/")));
    }
}

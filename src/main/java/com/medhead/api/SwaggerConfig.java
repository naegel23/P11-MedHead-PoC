package com.medhead.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI medHeadOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PoCAPIHeadMed")
                        .description("Api pour le PoC HeadMed")
                        .version("0.1"));
    }
}

package com.medhead.api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.time.LocalDate;
import java.util.function.Predicate;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Method to set paths to be included through swagger
     *
     * @return Docket
     */
    @Bean
    public Docket configApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).pathMapping("/").select()
                .paths(regex("/.*")).build();
    }


    /**
     * Method to set swagger info
     *
     * @return ApiInfoBuilder
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("PoCAPIHeadMed").description("Api pour le PoC HeadMed").version("0.1").build();
    }
}

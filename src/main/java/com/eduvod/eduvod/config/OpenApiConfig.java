package com.eduvod.eduvod.config;

import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduVOD API")
                        .version("1.0.0")
                        .description("Backend APIs for the EduVOD System")
                        .contact(new Contact()
                                .name("Blair Vullu")
                                .email("support@eduvod.com")
                                .url("https://eduvod.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                );
    }

    // Optional: Grouping APIs (e.g. superadmin, schooladmin)
    @Bean
    public GroupedOpenApi superAdminApi() {
        return GroupedOpenApi.builder()
                .group("superadmin")
                .pathsToMatch("/api/v1/superadmin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi schoolAdminApi() {
        return GroupedOpenApi.builder()
                .group("schooladmin")
                .pathsToMatch("/api/v1/schooladmin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }
}

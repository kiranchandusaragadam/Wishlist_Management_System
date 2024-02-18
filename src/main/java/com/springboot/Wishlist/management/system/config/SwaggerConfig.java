package com.springboot.Wishlist.management.system.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Wishlist Management System")
                        .description("This is Wishlist Management System backend application using spring boot, that features JWT authenticated and authorized Rest APIs to perform CRUD operations on user's wishlist at their own premises.")
                        .version("1.0.0").contact(new Contact().name("Kiranchandu Saragadam")
                                .email( "kiransaragadam247@gmail.com").url("https://github.com/kiranchandusaragadam/Wishlist_Management_System"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
}

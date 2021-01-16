package com.enat.sharemanagement.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("openId", new SecurityScheme()
                                .type(SecurityScheme.Type.OPENIDCONNECT)

                                .openIdConnectUrl("http://10.1.12.121:8081/auth/realms/enat/.well-known/openid-configuration")
                        ))
                .security(List.of(new SecurityRequirement().addList("openId")))

                .info(new Info().title("Enat Remedy API").description(
                        "Human resource management system for Medium and Large companies")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact().email("isd@enatbanksc.com").name("ISD Department")));
    }


}
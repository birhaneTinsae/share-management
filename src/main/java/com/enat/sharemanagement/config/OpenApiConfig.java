package com.enat.sharemanagement.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Share Management System API",
        version = "0.0.1-SNAPSHOT",
        description = "Share and shareholder management system for Medium and Large share companies",
        contact = @Contact(email = "birhane.tinsaa@gmail.com", url = "share@management.com")

), security = {
        @SecurityRequirement(name = "bearerAuth")
},
        servers = {
                @Server(
                        url = "http://10.1.80.54:8080/",
                        description = "DEV Server"
                ),
                @Server(
                        url = "http://10.1.80.54:8080/",
                        description = "PROD Server"
                )})

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                                .addSecuritySchemes("jwt",
//                                        new SecurityScheme()
//                                                .name("")
//                                                .type(SecurityScheme.Type.HTTP)
//                                                .scheme("bearer")
//                                                .bearerFormat("JWT")
//
//                                )
////                        .addSecuritySchemes("openId", new SecurityScheme()
////                                .type(SecurityScheme.Type.OPENIDCONNECT)
////
////                                .openIdConnectUrl("http://10.1.12.121:8081/auth/realms/enat/.well-known/openid-configuration")
////                        )
//                )
//                .security(List.of(new SecurityRequirement().addList("jwt")))
//
//                .info(new Info().title("Share Management System API").description(
//                        "Share and shareholder management system for Medium and Large share companies")
//                        .version("0.0.1-SNAPSHOT")
//                        .contact(new Contact().email("isd@enatbanksc.com").name("ISD Department")));
//    }


}
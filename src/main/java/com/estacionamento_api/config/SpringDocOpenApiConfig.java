package com.estacionamento_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return  new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API Para Gestão de Estacionamento de Veiculos")
                                .version("v1")
                                .license(new License().name("Apache 2.0 ").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Wiliam Reis").name("Wiliamsup83@gmail.com"))
                );
    }
}




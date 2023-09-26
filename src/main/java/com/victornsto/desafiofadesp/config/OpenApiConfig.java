package com.victornsto.desafiofadesp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio FADESP")
                        .version("/v1")
                        .description("API que possibilita o recebimento de pagamentos de débitos de pessoas físicas e jurídicas"));
    }
}

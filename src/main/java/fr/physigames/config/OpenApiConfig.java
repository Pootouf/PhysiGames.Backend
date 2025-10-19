package fr.physigames.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI physigamesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PhysiGames API")
                        .description("API de gestion de l'archive des jeux physiques")
                        .version("v0.0.1")
                        .license(new License()
                                .name("License du projet")
                                .url("https://github.com/Pootouf/PhysiGames.Backend")));
    }
}


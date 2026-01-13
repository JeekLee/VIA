package com.via.runnable.api.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "AccessToken Authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "access-token"
)
@SecurityScheme(
        name = "RefreshToken Authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "refresh-token"
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("VIA API")
                .description("VIA API document by [@JeekLee](https://github.com/JeekLee)");


        Server localServer = new Server();
        localServer.setDescription("VIA API local server");
        localServer.setUrl("http://localhost:8080");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .security(List.of(
                        new SecurityRequirement().addList("AccessToken Authentication"),
                        new SecurityRequirement().addList("RefreshToken Authentication")
                ));
    }
}

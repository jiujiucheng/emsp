package com.edwin.emsp.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jiucheng
 * @Description: swagger 配置
 * @Date: 2025/4/12
 */
@Configuration
public class OpenApiConfig  {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0")
                        .description("Emsp API Documentation")
                        .license(new License().name("Apache 2.0"))
                )
                .externalDocs(new ExternalDocumentation()
                        .url("https://github.com/jiujiucheng/emsp")
                        .description("a project about the emsp")
                );
    }
}
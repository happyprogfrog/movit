package com.happyprogfrog.movit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "API 명세서",
                description = "나만의 영화 리뷰 서비스 <무빗무빗>의 API 명세서",
                version = "1.0")
)
@Configuration
public class SwaggerConfig {
}
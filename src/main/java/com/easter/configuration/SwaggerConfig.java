package com.easter.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwaggerConfig {
    private final SwaggerInfo swaggerInfo;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.easter"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description(swaggerInfo.getDescription())
                .title(swaggerInfo.getTitle())
                .version(swaggerInfo.getVersion())
                .build();
    }
}

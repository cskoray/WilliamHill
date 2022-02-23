package com.williamHill.scoreBoard.config.swagger;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class OpenApiConfig {

  private static final String EXCLUDED_PATHS = "(/info|/health|/health/.*|/metrics|/error|/env|/env/.*)";

  @Bean
  public Docket api() {
    return new Docket(SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(not(PathSelectors.regex(EXCLUDED_PATHS)))
        .build();
  }
}

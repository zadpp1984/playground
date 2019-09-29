package org.cay.play.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.cay.play")) // 注意修改此处的包名
                .paths(PathSelectors.any())
                .build();
    }

    @Value("${spring.application.name}")
    String application_name;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(application_name + "接口列表") // 任意，请稍微规范点
                .description(application_name + "的接口说明文档") // 任意，请稍微规范点
                .build();
    }

}

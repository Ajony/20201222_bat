package com.h3c.vdi.viewscreen.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import com.h3c.vdi.viewscreen.utils.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {


    @Bean(value = "restApi")
    public Docket restApi() {
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        List<Parameter> parameters = Lists.newArrayList();
//        parameterBuilder.name("token").description("token令牌").modelRef(new ModelRef("String"))
//                .parameterType("header")
//                .required(true)
//                .build();
//        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("大屏接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.h3c.vdi.viewscreen.web"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(parameters)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger-bootstrap-ui RESTFul APIs")
                .description("API文档")
                .termsOfServiceUrl("http://localhost:18989/bigscreen")
                .contact(new Contact("lgq", "h3c.com", "xxx@mail.com"))
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
//        return new ApiKey("BearerToken", "Authorization", "header");
        return new ApiKey("BearerToken", JwtUtil.TOKEN_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }


}

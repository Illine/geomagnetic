package ru.illine.weather.geomagnetic.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.illine.weather.geomagnetic.config.property.ApiKeySecurityProperties;
import ru.illine.weather.geomagnetic.config.property.SwaggerProperties;
import ru.illine.weather.geomagnetic.model.dto.BaseResponse;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private static final String LICENCE = "License of API";
    private static final String ERROR_RESPONSE_NAME = BaseResponse.class.getSimpleName();
    private static final String NOT_FOUND_MESSAGE = "Data Not Found";
    private static final String INTERNAL_SERVER_MESSAGE = "Server Error";
    private static final String FORBIDDEN_MESSAGE = "Not Authorized";

    private final SwaggerProperties properties;

    @Autowired
    SwaggerConfig(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    Docket api(ApiInfo apiInfo,
               List<ResponseMessage> getGlobalResponses,
               List<ResponseMessage> patchGlobalResponses,
               List<Parameter> operationParameters) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(operationParameters)
                .globalResponseMessage(RequestMethod.GET, getGlobalResponses)
                .globalResponseMessage(RequestMethod.PATCH, patchGlobalResponses)
                .apiInfo(apiInfo);
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfo(
                properties.getTitle(),
                properties.getDescription(),
                properties.getVersion(),
                null,
                new Contact(
                        properties.getOwnerName(),
                        properties.getOwnerUrl(),
                        properties.getOwnerEmail()
                ),
                LICENCE,
                properties.getLicenceUrl(),
                Collections.emptyList());
    }

    @Bean
    List<ResponseMessage> getGlobalResponses() {
        return List.of(
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(NOT_FOUND_MESSAGE)
                        .responseModel(new ModelRef(ERROR_RESPONSE_NAME))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(INTERNAL_SERVER_MESSAGE)
                        .responseModel(new ModelRef(ERROR_RESPONSE_NAME))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(FORBIDDEN_MESSAGE)
                        .responseModel(new ModelRef(ERROR_RESPONSE_NAME))
                        .build()
        );
    }

    @Bean
    List<ResponseMessage> patchGlobalResponses() {
        return List.of(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(INTERNAL_SERVER_MESSAGE)
                        .responseModel(new ModelRef(ERROR_RESPONSE_NAME))
                        .build()
        );
    }

    @Bean
    List<Parameter> operationParameters(ApiKeySecurityProperties properties) {
        return List.of(
                new ParameterBuilder()
                        .name(properties.getHeaderName())
                        .description("A key allows to get access in the API")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true)
                        .build()
        );
    }
}

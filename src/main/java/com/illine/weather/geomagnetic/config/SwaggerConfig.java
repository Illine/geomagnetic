package com.illine.weather.geomagnetic.config;

import com.illine.weather.geomagnetic.config.property.SwaggerProperties;
import com.illine.weather.geomagnetic.model.dto.BaseResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String LICENCE = "License of API";
    private static final String ERROR_RESPONSE_NAME = BaseResponse.class.getSimpleName();
    private static final String NOT_FOUND_MESSAGE = "Data Not Found";
    private static final String INTERNAL_SERVER_MESSAGE = "Server Error";

    private final SwaggerProperties properties;

    @Autowired
    SwaggerConfig(SwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    Docket api(ApiInfo apiInfo, RelativePathProvider relativePathProvider,
               List<ResponseMessage> getGlobalResponses, List<ResponseMessage> patchGlobalResponses) {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(relativePathProvider)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
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
    RelativePathProvider relativePathProvider(ServletContext servletContext) {
        return new GeomagneticRelativePathProvider(servletContext, properties.getIngressPath());
    }

    static class GeomagneticRelativePathProvider extends RelativePathProvider {

        private final String rootPath;

        public GeomagneticRelativePathProvider(ServletContext servletContext, String rootPath) {
            super(servletContext);
            this.rootPath = rootPath;
        }

        @Override
        protected String applicationPath() {
            return UriComponentsBuilder.newInstance().path(rootPath).path(super.applicationPath()).toUriString();
        }
    }
}

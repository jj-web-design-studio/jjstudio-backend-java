package com.jjstudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.jjstudio.controller.SoundController.SOUNDS_SWAGGER_GROUP_NAME;
import static com.jjstudio.controller.TrackController.TRACKS_SWAGGER_GROUP_NAME;
import static com.jjstudio.controller.UserController.USERS_SWAGGER_GROUP_NAME;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket usersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jjstudio.controller"))
                .paths(regex("/users.*"))
                .build()
                .groupName(USERS_SWAGGER_GROUP_NAME);
    }

    @Bean
    public Docket soundsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jjstudio.controller"))
                .paths(regex("/sounds.*"))
                .build()
                .groupName(SOUNDS_SWAGGER_GROUP_NAME);
    }

    @Bean
    public Docket tracksApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jjstudio.controller"))
                .paths(regex("/tracks.*"))
                .build()
                .groupName(TRACKS_SWAGGER_GROUP_NAME);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

package com.inerpapi;

import com.inerpapi.config.property.InerpApiProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(InerpApiProperty.class)
public class InerpApiApplication {

    @Autowired
    private InerpApiProperty inerpApiProperty;

    public static void main(String[] args) {
        SpringApplication.run(InerpApiApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins(inerpApiProperty.getOriginAllowed());
            }
        };
    }
}

package com.nest.chatsphere.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${external.static.path}") // Read external path from application.properties or environment variable
    private String externalStaticPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from the external directory
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + externalStaticPath);
    }
}

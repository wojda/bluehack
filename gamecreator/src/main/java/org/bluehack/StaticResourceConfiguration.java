package org.bluehack;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String tempDir = System.getProperty("java.io.tmpdir");
        new File(tempDir+"/games").mkdirs();
        registry.addResourceHandler("/games/**").addResourceLocations("file:"+tempDir+"/games/");
    }
}
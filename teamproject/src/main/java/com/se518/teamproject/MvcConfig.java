package com.se518.teamproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("Index");
        registry.addViewController("/index").setViewName("Index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/landing").setViewName("LandingPage");
    }
}

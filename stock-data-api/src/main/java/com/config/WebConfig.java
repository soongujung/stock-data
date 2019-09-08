package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/m/")
                .setCachePeriod(20);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/auth/slack/redirect")     // or "/**"
        registry.addMapping("/**")
                .allowedOrigins("http://slack.com","https://slack.com")
//                .allowedOrigins("*")
                .allowedHeaders("Access-Control-Allow-Origin")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PUT.name()
                );
    }


    //    @Bean
//    public WebMvcConfigurer webMvcConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/auth/slack/redirect")
//                        .allowedOrigins("*")
//                        .allowedMethods(HttpMethod.GET.name())
//                        .maxAge(1000);
//            }
//        };
//    }
}

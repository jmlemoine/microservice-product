package com.example.microservicezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class MicroserviceZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceZuulApplication.class, args);
        System.out.println("Klk Zuul");
    }

   /* @Bean
    //@Configuration
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(/*"/**"*//*"/*")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        //.maxAge(3600L)
                        .allowedHeaders("*")
                        //.exposedHeaders("Authorization")
                        .allowCredentials(true);
            }
        };
    }*/

    /*@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/
   /* public CorsFilter corsFilter() {
    config.setAllowCredentials(true);
config.addAllowedOrigin("");
config.addAllowedHeader("");
config.addAllowedMethod("");
source.registerCorsConfiguration("", config);*/

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("");
        config.addAllowedHeader("");
        config.addAllowedMethod("");
        source.registerCorsConfiguration("", config);
        return new CorsFilter(source);
    }

}

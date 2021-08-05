package com.pdefence;

import com.pdefence.contoller.UserController;
import com.pdefence.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectDefenceApplication extends SpringBootServletInitializer {


//    @Bean
//    CommandLineRunner init(UserController userController) {
//        return args -> {
//            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
//                User user = new User(name, name.toLowerCase() + "@domain.com");
//                try {
//                    userController.createUser(user);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//            userController.getUsers().forEach(System.out::println);
//        };
//    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProjectDefenceApplication .class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectDefenceApplication.class, args);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter((CorsConfigurationSource) source);
//    }
}

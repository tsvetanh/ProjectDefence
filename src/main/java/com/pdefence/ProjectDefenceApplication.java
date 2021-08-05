package com.pdefence;

import com.pdefence.contoller.UserController;
import com.pdefence.entity.User;
//import com.pdefence.repository.UserRepository;
import com.pdefence.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@SpringBootApplication
public class ProjectDefenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDefenceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserController userController) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                try {
                    userController.createUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            userController.getUsers().forEach(System.out::println);
        };
    }

}

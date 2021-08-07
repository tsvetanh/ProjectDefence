package com.pdefence.contoller;

import com.pdefence.entity.User;
import com.pdefence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://project-defence.vercel.app"}, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userService.getAllUsers();
    }


    @PostMapping("/users/register")
    public User registerUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
         return userService.saveUserDetails(user);
    }
//
//    @PostMapping("/users")
//    public void addUser(@RequestBody User user) {
//        userRepository.save(user);
//    }


    @PostMapping("/users/login")
    public User getUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
        return userService.getUserDetails(user);
    }

    @PostMapping("/users/logout")
    public void logout() {
    }

    @PutMapping("/users/profile")
    public User updateUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
        return userService.updateUserDetails(user);
    }
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String name){
        return userService.deleteUser(name);
    }
}

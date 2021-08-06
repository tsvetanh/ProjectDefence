package com.pdefence.contoller;

import com.pdefence.entity.User;
import com.pdefence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userService.getAllUsers();
    }


    @PostMapping("/users/register")
    public void registerUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
         userService.saveUserDetails(user);
    }
//
//    @PostMapping("/users")
//    public void addUser(@RequestBody User user) {
//        userRepository.save(user);
//    }

    @GetMapping("/getUserDetails")
    public User getUser(@RequestParam String name ) throws InterruptedException, ExecutionException {
        return userService.getUserDetails(name);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user ) throws InterruptedException, ExecutionException {
        return userService.updateUserDetails(user);
    }
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String name){
        return userService.deleteUser(name);
    }
}

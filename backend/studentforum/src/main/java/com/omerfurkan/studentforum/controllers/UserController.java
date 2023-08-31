package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserUpdateRequest;
import com.omerfurkan.studentforum.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public User createNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createNewUser(userCreateRequest);
    }

    @PutMapping("/{userId}")
    public User updateUserById(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserById(userId, userUpdateRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

}

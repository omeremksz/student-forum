package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Profile;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserUpdateRequest;
import com.omerfurkan.studentforum.services.ProfileService;
import com.omerfurkan.studentforum.services.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public User createNewUserWithProfile(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createNewUserWithProfile(userCreateRequest);
    }

    @PostMapping("activate/{userId}")
    public User activateUserById(@PathVariable Long userId) {
        return userService.activateUser(userId);
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

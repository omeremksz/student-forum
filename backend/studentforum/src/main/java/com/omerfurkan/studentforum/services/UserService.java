package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.UserRepository;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User createNewUser(UserCreateRequest userCreateRequest) {
        //User credentials validation
        User userToSave = new User();
        userToSave.setUserName(userCreateRequest.getUserName());
        userToSave.setPassword(userCreateRequest.getPassword());
        userToSave.setEmail(userCreateRequest.getEmail());
        return userRepository.save(userToSave);
    }

    public User updateUserById(Long userId, UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User userToUpdate = user.get();

            userToUpdate.setUserName(userUpdateRequest.getUserName());
            userToUpdate.setPassword(userUpdateRequest.getPassword());
            userToUpdate.setEmail(userUpdateRequest.getEmail());

            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}

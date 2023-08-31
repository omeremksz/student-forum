package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.entities.UserInteraction;
import com.omerfurkan.studentforum.repositories.UserInteractionRepository;
import com.omerfurkan.studentforum.requests.UserInteractionCreateRequest;
import com.omerfurkan.studentforum.requests.UserInteractionUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserInteractionService {
    private UserInteractionRepository userInteractionRepository;
    private UserService userService;

    public UserInteractionService(UserInteractionRepository userInteractionRepository, UserService userService) {
        this.userInteractionRepository = userInteractionRepository;
        this.userService = userService;
    }

    public List<UserInteraction> getAllUserInteractions() {
        return userInteractionRepository.findAll();
    }

    public UserInteraction getUserInteractionById(Long userInteractionId) {
        return userInteractionRepository.findById(userInteractionId).orElse(null);
    }

    public UserInteraction createNewUserInteraction(UserInteractionCreateRequest userInteractionCreateRequest) {
        User user = userService.getUserById(userInteractionCreateRequest.getUserId());

        if (user == null) {
            return null;
        } else {
            UserInteraction userInteractionToSave = new UserInteraction();

            userInteractionToSave.setUser(user);
            userInteractionToSave.setInteractionType(userInteractionCreateRequest.getInteractionType());
            userInteractionToSave.setReferenceId(userInteractionCreateRequest.getReferenceId());
            userInteractionToSave.setReferenceType(userInteractionCreateRequest.getReferenceType());
            userInteractionToSave.setInteractionDate(LocalDateTime.now());

            return userInteractionRepository.save(userInteractionToSave);
        }
    }

    public UserInteraction updateUserInteractionById(Long userInteractionId, UserInteractionUpdateRequest userInteractionUpdateRequest) {
        Optional<UserInteraction> userInteraction = userInteractionRepository.findById(userInteractionId);
        User user = userService.getUserById(userInteractionUpdateRequest.getUserId());

        if (userInteraction.isPresent() && user != null) {
            UserInteraction userInteractionToUpdate = userInteraction.get();

            userInteractionToUpdate.setUser(user);
            userInteractionToUpdate.setInteractionType(userInteractionUpdateRequest.getInteractionType());
            userInteractionToUpdate.setReferenceId(userInteractionUpdateRequest.getReferenceId());
            userInteractionToUpdate.setReferenceType(userInteractionUpdateRequest.getReferenceType());
            userInteractionToUpdate.setReferenceType(userInteractionUpdateRequest.getReferenceType());
            userInteractionToUpdate.setInteractionDate(LocalDateTime.now());

            return userInteractionRepository.save(userInteractionToUpdate);
        } else {
            return null;
        }
    }

    public void deleteUserInteractionById(Long userInteractionId) {
        userInteractionRepository.deleteById(userInteractionId);
    }
}

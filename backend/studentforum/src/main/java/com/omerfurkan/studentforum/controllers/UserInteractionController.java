package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.UserInteraction;
import com.omerfurkan.studentforum.requests.UserInteractionCreateRequest;
import com.omerfurkan.studentforum.requests.UserInteractionUpdateRequest;
import com.omerfurkan.studentforum.responses.CommentResponse;
import com.omerfurkan.studentforum.responses.PostResponse;
import com.omerfurkan.studentforum.responses.VoteResponse;
import com.omerfurkan.studentforum.services.UserInteractionService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-interactions")
public class UserInteractionController {
    private UserInteractionService userInteractionService;

    public UserInteractionController(UserInteractionService userInteractionService) {
        this.userInteractionService = userInteractionService;
    }

    @GetMapping
    public List<UserInteraction> getAllUserInteractions() {
        return userInteractionService.getAllUserInteractions();
    }

    @GetMapping("/{userInteractionId}")
    public UserInteraction getUserInteractionById(@PathVariable Long userInteractionId) {
        return userInteractionService.getUserInteractionById(userInteractionId);
    }

    @GetMapping("/post/{userId}")
    public List<PostResponse> getUserPostInteractionByUserId(@PathVariable Long userId) {
        return userInteractionService.getUserPostInteractionByUserId(userId);
    }

    @GetMapping("/comment/{userId}")
    public List<CommentResponse> getUserCommentInteractionByUserId(@PathVariable Long userId) {
        return userInteractionService.getUserCommentInteractionByUserId(userId);
    }

    @PostMapping("/post")
    public UserInteraction createNewUserPostInteraction(@RequestBody UserInteractionCreateRequest userInteractionCreateRequest) {
        return userInteractionService.createNewUserPostInteraction(userInteractionCreateRequest);
    }

    @PostMapping("/comment")
    public UserInteraction createNewUserCommentInteraction(@RequestBody UserInteractionCreateRequest userInteractionCreateRequest) {
        return userInteractionService.createNewUserCommentInteraction(userInteractionCreateRequest);
    }

    @PostMapping("/vote")
    public UserInteraction createNewUserVoteInteraction(@RequestBody UserInteractionCreateRequest userInteractionCreateRequest) {
        return userInteractionService.createNewUserVoteInteraction(userInteractionCreateRequest);
    }

    @PutMapping("/{userInteractionId}")
    public UserInteraction updateUserInteractionById(@PathVariable Long userInteractionId,
                                                     @RequestBody UserInteractionUpdateRequest userInteractionUpdateRequest) {
        return userInteractionService.updateUserInteractionById(userInteractionId, userInteractionUpdateRequest);
    }

    @DeleteMapping("/{userInteractionId}")
    public void deleteUserInteractionById(@PathVariable Long userInteractionId) {
        userInteractionService.deleteUserInteractionById(userInteractionId);
    }
}

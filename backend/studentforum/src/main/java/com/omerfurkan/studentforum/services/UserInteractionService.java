package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.*;
import com.omerfurkan.studentforum.repositories.UserInteractionRepository;
import com.omerfurkan.studentforum.requests.UserInteractionCreateRequest;
import com.omerfurkan.studentforum.requests.UserInteractionUpdateRequest;

import com.omerfurkan.studentforum.responses.CommentResponse;
import com.omerfurkan.studentforum.responses.PostResponse;
import com.omerfurkan.studentforum.responses.VoteResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserInteractionService {
    private UserInteractionRepository userInteractionRepository;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private VoteService voteService;

    public UserInteractionService(UserInteractionRepository userInteractionRepository, UserService userService, @Lazy PostService postService, @Lazy CommentService commentService, @Lazy VoteService voteService) {
        this.userInteractionRepository = userInteractionRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.voteService = voteService;
    }

    public List<UserInteraction> getAllUserInteractions() {
        return userInteractionRepository.findAll();
    }

    public UserInteraction getUserInteractionById(Long userInteractionId) {
        return userInteractionRepository.findById(userInteractionId).orElse(null);
    }

    public List<PostResponse> getUserPostInteractionByUserId(Long userId) {
        List<UserInteraction> userPostInteractions = userInteractionRepository.findByUserIdAndPostIsNotNull(userId);

        List<Post> userPostList = userPostInteractions.stream()
                .map(i -> postService.getPostById(i.getReferenceId()))
                .collect(Collectors.toList());

        return userPostList.stream().map(p -> {
            List<VoteResponse> postVotes = voteService.getAllVotes(Optional.ofNullable(null), Optional.of(p.getId()), Optional.ofNullable(null));
            return new PostResponse(p, postVotes);}).collect(Collectors.toList());
    }

    public List<CommentResponse> getUserCommentInteractionByUserId(Long userId) {
        List<UserInteraction> userCommentInteractions = userInteractionRepository.findByUserIdAndCommentIsNotNull(userId);

        List<Comment> userCommentList = userCommentInteractions.stream()
                .map(i -> commentService.getCommentById(i.getReferenceId()))
                .collect(Collectors.toList());

        return userCommentList.stream().map(c -> {
            List<VoteResponse> commentVotes = voteService.getAllVotes(Optional.ofNullable(null), Optional.ofNullable(null), Optional.of(c.getId()));
            return new CommentResponse(c, commentVotes);}).collect(Collectors.toList());
    }

    public UserInteraction createNewUserPostInteraction(UserInteractionCreateRequest userInteractionCreateRequest) {
        User user = userService.getUserById(userInteractionCreateRequest.getUserId());
        Post post = postService.getPostById(userInteractionCreateRequest.getReferenceId());

        if (user == null || post == null) {
            return null;
        } else {
            UserInteraction userInteractionToSave = new UserInteraction();

            userInteractionToSave.setUser(user);
            userInteractionToSave.setPost(post);
            userInteractionToSave.setComment(null);
            userInteractionToSave.setVote(null);
            userInteractionToSave.setReferenceId(userInteractionCreateRequest.getReferenceId());
            userInteractionToSave.setReferenceType(userInteractionCreateRequest.getReferenceType());
            userInteractionToSave.setInteractionDate(LocalDateTime.now());

            return userInteractionRepository.save(userInteractionToSave);
        }
    }

    public UserInteraction createNewUserCommentInteraction(UserInteractionCreateRequest userInteractionCreateRequest) {
        User user = userService.getUserById(userInteractionCreateRequest.getUserId());
        Comment comment = commentService.getCommentById(userInteractionCreateRequest.getReferenceId());

        if (user == null || comment == null) {
            return null;
        } else {
            UserInteraction userInteractionToSave = new UserInteraction();

            userInteractionToSave.setUser(user);
            userInteractionToSave.setPost(null);
            userInteractionToSave.setComment(comment);
            userInteractionToSave.setVote(null);
            userInteractionToSave.setReferenceId(userInteractionCreateRequest.getReferenceId());
            userInteractionToSave.setReferenceType(userInteractionCreateRequest.getReferenceType());
            userInteractionToSave.setInteractionDate(LocalDateTime.now());

            return userInteractionRepository.save(userInteractionToSave);
        }
    }

    public UserInteraction createNewUserVoteInteraction(UserInteractionCreateRequest userInteractionCreateRequest) {
        User user = userService.getUserById(userInteractionCreateRequest.getUserId());
        Vote vote = voteService.getVoteById(userInteractionCreateRequest.getReferenceId());

        if (user == null || vote == null) {
            return null;
        } else {
            UserInteraction userInteractionToSave = new UserInteraction();

            userInteractionToSave.setUser(user);
            userInteractionToSave.setPost(null);
            userInteractionToSave.setComment(null);
            userInteractionToSave.setVote(vote);
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

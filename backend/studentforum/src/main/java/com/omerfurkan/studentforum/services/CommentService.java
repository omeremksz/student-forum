package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.CommentRepository;
import com.omerfurkan.studentforum.requests.CommentCreateRequest;
import com.omerfurkan.studentforum.requests.CommentUpdateRequest;
import com.omerfurkan.studentforum.requests.UserInteractionCreateRequest;
import com.omerfurkan.studentforum.responses.CommentResponse;
import com.omerfurkan.studentforum.responses.PostResponse;
import com.omerfurkan.studentforum.responses.VoteResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;
    private VoteService voteService;
    private UserInteractionService userInteractionService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService, VoteService voteService, UserInteractionService userInteractionService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.voteService = voteService;
        this.userInteractionService = userInteractionService;
    }

    public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> commentList;
        if (userId.isPresent() && postId.isPresent()) {
            commentList = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            commentList = commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            commentList = commentRepository.findByPostId(postId.get());
        } else {
            commentList = commentRepository.findAll();
        }
        return commentList.stream().map(c -> {
            List<VoteResponse> commentVotes = voteService.getAllVotes(Optional.ofNullable(null), Optional.ofNullable(null), Optional.of(c.getId()));
            return new CommentResponse(c, commentVotes);}).collect(Collectors.toList());
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createNewComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());

        if (user == null || post == null ){
            return null;
        } else {
            Comment commentToSave = new Comment();

            commentToSave.setUser(user);
            commentToSave.setPost(post);
            commentToSave.setContentText(commentCreateRequest.getContentText());
            commentToSave.setCreationDate(LocalDateTime.now());
            commentToSave.setUpdateDate(LocalDateTime.now());

            commentRepository.save(commentToSave);

            UserInteractionCreateRequest userInteractionCreateRequest = getUserCommentInteractionCreateRequest(user, commentToSave);

            userInteractionService.createNewUserCommentInteraction(userInteractionCreateRequest);

            return commentToSave;
        }
    }

    private static UserInteractionCreateRequest getUserCommentInteractionCreateRequest(User user, Comment commentToSave) {
        UserInteractionCreateRequest userInteractionCreateRequest = new UserInteractionCreateRequest();

        userInteractionCreateRequest.setUserId(user.getId());
        userInteractionCreateRequest.setReferenceId(commentToSave.getId());
        userInteractionCreateRequest.setReferenceType("post");

        return userInteractionCreateRequest;
    }

    public Comment updateCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();

            commentToUpdate.setContentText(commentUpdateRequest.getContentText());
            commentToUpdate.setUpdateDate(LocalDateTime.now());

            return commentRepository.save(commentToUpdate);
        } else {
            return null;
        }
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

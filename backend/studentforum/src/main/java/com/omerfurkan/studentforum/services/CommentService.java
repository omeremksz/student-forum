package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.CommentRepository;
import com.omerfurkan.studentforum.requests.CommentCreateRequest;
import com.omerfurkan.studentforum.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
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

            return commentRepository.save(commentToSave);
        }
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

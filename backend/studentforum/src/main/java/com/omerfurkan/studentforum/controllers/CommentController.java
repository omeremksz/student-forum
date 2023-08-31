package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.requests.CommentCreateRequest;
import com.omerfurkan.studentforum.requests.CommentUpdateRequest;
import com.omerfurkan.studentforum.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public Comment createNewComment(@RequestBody CommentCreateRequest commentCreateRequest) {
        return commentService.createNewComment(commentCreateRequest);
    }

    @PutMapping("/{commentId}")
    public Comment updateCommentById(@PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.updateCommentById(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }
}

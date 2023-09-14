package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.requests.PostCreateRequest;
import com.omerfurkan.studentforum.requests.PostUpdateRequest;
import com.omerfurkan.studentforum.responses.PostResponse;
import com.omerfurkan.studentforum.services.PostService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public Post createNewPost(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.createNewPost(postCreateRequest);
    }

    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.updatePostById(postId, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }
}

package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.requests.PostCreateRequest;
import com.omerfurkan.studentforum.requests.PostUpdateRequest;
import com.omerfurkan.studentforum.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
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

package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.PostPreferences;
import com.omerfurkan.studentforum.requests.PostPreferencesCreateRequest;
import com.omerfurkan.studentforum.requests.PostPreferencesUpdateRequest;
import com.omerfurkan.studentforum.services.PostPreferencesService;
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
@RequestMapping("/post-preferences")
public class PostPreferencesController {
    private PostPreferencesService postPreferencesService;

    public PostPreferencesController(PostPreferencesService postPreferencesService) {
        this.postPreferencesService = postPreferencesService;
    }

    @GetMapping
    public List<PostPreferences> getAllPostPreferences() {
        return postPreferencesService.getAllPreferences();
    }

    @GetMapping("/{postPreferencesId}")
    public PostPreferences getPostPreferencesById(@PathVariable Long postPreferencesId) {
        return postPreferencesService.getPostPreferencesById(postPreferencesId);
    }

    @PostMapping
    public PostPreferences createNewPostPreferences(@RequestBody PostPreferencesCreateRequest postPreferencesCreateRequest) {
        return postPreferencesService.createNewPostPreferences(postPreferencesCreateRequest);
    }

    @PutMapping("/{postPreferencesId}")
    public PostPreferences updatePostPreferencesById(@PathVariable Long postPreferencesId,
                                                     @RequestBody PostPreferencesUpdateRequest postPreferencesUpdateRequest) {
        return postPreferencesService.updatePostPreferencesById(postPreferencesId, postPreferencesUpdateRequest);
    }

    @DeleteMapping("/{postPreferencesId}")
    public void deletePostPreferencesById(@PathVariable Long postPreferencesId) {
        postPreferencesService.deletePostPreferencesById(postPreferencesId);
    }
}

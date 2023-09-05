package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.PostPreferences;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.PostRepository;
import com.omerfurkan.studentforum.requests.PostCreateRequest;
import com.omerfurkan.studentforum.requests.PostUpdateRequest;
import com.omerfurkan.studentforum.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private PostPreferencesService postPreferencesService;
    private UserService userService;

    public PostService(PostRepository postRepository, PostPreferencesService postPreferencesService, UserService userService) {
        this.postRepository = postRepository;
        this.postPreferencesService = postPreferencesService;
        this.userService = userService;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(p -> new PostResponse(p)).collect(Collectors.toList());
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createNewPost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        PostPreferences postPreferences = postPreferencesService.getPostPreferencesById(postCreateRequest.getPostPreferencesId());
        if (user == null || postPreferences == null) {
            return null;
        } else {
            Post postToSave = new Post();

            postToSave.setUser(user);
            postToSave.setPostPreferences(postPreferences);
            postToSave.setContentText(postCreateRequest.getContentText());
            postToSave.setContentPictureURL(postCreateRequest.getContentPictureURL());
            postToSave.setCreationDate(LocalDateTime.now());
            postToSave.setUpdateDate(LocalDateTime.now());

            return postRepository.save(postToSave);
        }
    }

    public Post updatePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        PostPreferences postPreferences = postPreferencesService.getPostPreferencesById(postUpdateRequest.getPostPreferencesId());

        if (post.isPresent() && postPreferences != null) {
            Post postToUpdate = post.get();

            postToUpdate.setPostPreferences(postPreferences);
            postToUpdate.setContentText(postUpdateRequest.getContentText());
            postToUpdate.setUpdateDate(LocalDateTime.now());

            return postRepository.save(postToUpdate);
        } else {
            return null;
        }
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}

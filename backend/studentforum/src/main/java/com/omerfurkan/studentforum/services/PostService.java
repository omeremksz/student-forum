package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.PostPreferences;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.repositories.PostRepository;
import com.omerfurkan.studentforum.requests.PostCreateRequest;
import com.omerfurkan.studentforum.requests.PostUpdateRequest;
import com.omerfurkan.studentforum.responses.PostResponse;
import com.omerfurkan.studentforum.responses.VoteResponse;
import org.springframework.context.annotation.Lazy;
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
    private VoteService voteService;

    public PostService(PostRepository postRepository, PostPreferencesService postPreferencesService, UserService userService, VoteService voteService) {
        this.postRepository = postRepository;
        this.postPreferencesService = postPreferencesService;
        this.userService = userService;
        this.voteService = voteService;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(p -> {
            List<VoteResponse> postVotes = voteService.getAllVotes(Optional.ofNullable(null), Optional.of(p.getId()), Optional.ofNullable(null));
            return new PostResponse(p, postVotes);}).collect(Collectors.toList());
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
            postToSave.setContentImageURL(postCreateRequest.getContentImageURL());
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

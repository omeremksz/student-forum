package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.PostPreferences;
import com.omerfurkan.studentforum.repositories.PostPreferencesRepository;
import com.omerfurkan.studentforum.requests.PostPreferencesCreateRequest;
import com.omerfurkan.studentforum.requests.PostPreferencesUpdateRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PostPreferencesService {
    private PostPreferencesRepository postPreferencesRepository;

    public PostPreferencesService(PostPreferencesRepository postPreferencesRepository) {
        this.postPreferencesRepository = postPreferencesRepository;
    }


    public List<PostPreferences> getAllPreferences() {
        return postPreferencesRepository.findAll();
    }

    public PostPreferences getPostPreferencesById(Long postPreferencesId) {
        return postPreferencesRepository.findById(postPreferencesId).orElse(null);
    }

    public PostPreferences createNewPostPreferences(PostPreferencesCreateRequest postPreferencesCreateRequest) {
        PostPreferences postPreferencesToSave = new PostPreferences();

        postPreferencesToSave.setInteractionEnvironment(postPreferencesCreateRequest.getInteractionEnvironment());
        postPreferencesToSave.setPostCategory(postPreferencesCreateRequest.getPostCategory());

        return postPreferencesRepository.save(postPreferencesToSave);
    }

    public PostPreferences updatePostPreferencesById(Long postPreferencesId, PostPreferencesUpdateRequest postPreferencesUpdateRequest) {
        Optional<PostPreferences> postPreferences = postPreferencesRepository.findById(postPreferencesId);

        if (postPreferences.isPresent()) {
            PostPreferences postPreferencesToUpdate = postPreferences.get();

            if (postPreferencesUpdateRequest.getInteractionEnvironment() == null &&
                postPreferencesUpdateRequest.getPostCategory() == null) {
                return postPreferencesRepository.save(postPreferencesToUpdate);
            } else if (postPreferencesUpdateRequest.getInteractionEnvironment() == null) {
                postPreferencesToUpdate.setPostCategory(postPreferencesUpdateRequest.getPostCategory());
            } else if (postPreferencesUpdateRequest.getPostCategory() == null) {
                postPreferencesToUpdate.setInteractionEnvironment(postPreferencesUpdateRequest.getInteractionEnvironment());
            } else {
                postPreferencesToUpdate.setInteractionEnvironment(postPreferencesUpdateRequest.getInteractionEnvironment());
                postPreferencesToUpdate.setPostCategory(postPreferencesUpdateRequest.getPostCategory());
            }

            return postPreferencesRepository.save(postPreferencesToUpdate);
        } else {
            return null;
        }
    }

    public void deletePostPreferencesById(Long postPreferencesId) {
        postPreferencesRepository.deleteById(postPreferencesId);
    }
}

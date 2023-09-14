package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Profile;
import com.omerfurkan.studentforum.requests.ProfileCreateRequest;
import com.omerfurkan.studentforum.requests.ProfileUpdateRequest;
import com.omerfurkan.studentforum.responses.ProfileResponse;
import com.omerfurkan.studentforum.services.ProfileService;
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
@RequestMapping("/profiles")
public class ProfileController {
    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{userId}")
    public ProfileResponse getProfileByUserId(@PathVariable Long userId) {
        return profileService.getProfileById(userId);
    }

    @PostMapping
    public Profile createNewProfile(@RequestBody ProfileCreateRequest profileCreateRequest) {
        return profileService.createNewProfile(profileCreateRequest);
    }

    @PutMapping("/{profileId}")
    public Profile updateProfileById(@PathVariable Long profileId, @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        return profileService.updateProfileById(profileId, profileUpdateRequest);
    }

    @DeleteMapping("/{profileId}")
    public void deleteProfileById(@PathVariable Long profileId) {
        profileService.deleteProfileById(profileId);
    }
}

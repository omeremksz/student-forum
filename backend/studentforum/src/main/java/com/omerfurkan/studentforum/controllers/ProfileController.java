package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Profile;
import com.omerfurkan.studentforum.requests.ProfileCreateRequest;
import com.omerfurkan.studentforum.requests.ProfileUpdateRequest;
import com.omerfurkan.studentforum.responses.ProfileResponse;
import com.omerfurkan.studentforum.services.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

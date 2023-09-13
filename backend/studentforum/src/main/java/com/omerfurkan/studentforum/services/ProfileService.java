package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Profile;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.ProfileRepository;
import com.omerfurkan.studentforum.requests.ProfileCreateRequest;
import com.omerfurkan.studentforum.requests.ProfileUpdateRequest;
import com.omerfurkan.studentforum.responses.ProfileResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;
    private UserService userService;

    public ProfileService(ProfileRepository profileRepository, UserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public ProfileResponse getProfileById(Long userId) {
        Profile profile = profileRepository.findByUserId(userId);
        return new ProfileResponse(profile);
    }

    public Profile createNewProfile(ProfileCreateRequest profileCreateRequest) {
        User user = userService.getUserById(profileCreateRequest.getUserId());

        if (user == null) {
            return null;
        } else {
            Profile profileToSave = new Profile();
            profileToSave.setUser(user);
            profileToSave.setFirstName(profileCreateRequest.getFirstName());
            profileToSave.setLastName(profileCreateRequest.getLastName());
            profileToSave.setEmail(profileCreateRequest.getEmail());
            profileToSave.setProfilePictureURL(profileCreateRequest.getProfilePictureURL());
            profileToSave.setAbout(profileCreateRequest.getAbout());

            return profileRepository.save(profileToSave);
        }
    }

    public Profile updateProfileById(Long profileId, ProfileUpdateRequest profileUpdateRequest) {
        Optional<Profile> profile = profileRepository.findById(profileId);

        if (profile.isPresent()) {
            Profile profileToUpdate = profile.get();

            profileToUpdate.setFirstName(profileUpdateRequest.getFirstName());
            profileToUpdate.setLastName(profileToUpdate.getLastName());
            profileToUpdate.setEmail(profileToUpdate.getEmail());
            profileToUpdate.setProfilePictureURL(profileUpdateRequest.getProfilePictureURL());
            profileToUpdate.setAbout(profileUpdateRequest.getAbout());

            return profileRepository.save(profileToUpdate);
        } else {
            return null;
        }
    }

    public void deleteProfileById(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}

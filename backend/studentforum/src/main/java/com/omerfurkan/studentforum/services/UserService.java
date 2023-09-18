package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.Profile;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.ProfileRepository;
import com.omerfurkan.studentforum.repositories.UserRepository;
import com.omerfurkan.studentforum.requests.UserCreateRequest;
import com.omerfurkan.studentforum.requests.UserUpdateRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User getUserByEducationalEmail(String educationalEmail) {
        return userRepository.findByEducationalEmail(educationalEmail);
    }

    @Transactional
    public User createNewUser(UserCreateRequest userCreateRequest) {

        User userToSave = new User();

        userToSave.setUserName(userCreateRequest.getUserName())
            .setPassword(userCreateRequest.getPassword())
            .setEducationalEmail(userCreateRequest.getEducationalEmail())
            .setIsActive(false);

        return userRepository.save(userToSave);
    }

    @Transactional
    public User createNewUserWithProfile(UserCreateRequest userCreateRequest) {
        User newUser = createNewUser(userCreateRequest);
        Profile profile = new Profile().setUser(userRepository.findById(newUser.getId()).orElse(null))
            .setFirstName(userCreateRequest.getUserName())
            .setLastName(null)
            .setEmail(userCreateRequest.getEducationalEmail())
            .setProfilePictureURL(null)
            .setAbout(null);

        profileRepository.save(profile);
        return newUser;
    }

    public User activateUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setIsActive(true);
            return userRepository.save(user);
        } else {
            return null;
        }
    }


    public User updateUserById(Long userId, UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User userToUpdate = user.get();

            userToUpdate.setUserName(userUpdateRequest.getUserName());
            userToUpdate.setPassword(userUpdateRequest.getPassword());
            userToUpdate.setEducationalEmail(userUpdateRequest.getEducationalEmail());

            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

}

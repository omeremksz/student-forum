package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private UserService userService;

    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);

        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userService.getUserById(id);

        return JwtUserDetails.create(user);
    }
}

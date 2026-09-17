package com.alin.task_management_project.services;

import com.alin.task_management_project.entities.User;
import com.alin.task_management_project.exceptions.UserByNameNotFoundException;
import com.alin.task_management_project.exceptions.UserNotFoundException;
import com.alin.task_management_project.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name){
        User user = userRepository.findByName(name).orElseThrow(
                () -> new UserByNameNotFoundException(name)
        );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

    }

}

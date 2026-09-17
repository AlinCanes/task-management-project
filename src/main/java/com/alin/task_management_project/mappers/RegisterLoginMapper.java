package com.alin.task_management_project.mappers;

import com.alin.task_management_project.dtos.RegisterLoginRequestDTO;
import com.alin.task_management_project.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RegisterLoginMapper implements Function<RegisterLoginRequestDTO, User> {


    @Override
    public User apply(RegisterLoginRequestDTO registerLoginRequestDTO) {
        User user = new User();
        user.setName(registerLoginRequestDTO.name());
        user.setPassword(registerLoginRequestDTO.password());

        return user;
    }
}

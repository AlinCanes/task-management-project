package com.alin.task_management_project.mappers;

import com.alin.task_management_project.dtos.UserRequestDTO;
import com.alin.task_management_project.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserRequestMapper implements Function<UserRequestDTO, User> {
    @Override
    public User apply(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        return user;
    }
}

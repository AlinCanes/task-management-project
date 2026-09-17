package com.alin.task_management_project.mappers;

import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.dtos.UserResponseDTO;
import com.alin.task_management_project.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserResponseMapper implements Function<User, UserResponseDTO> {
    private final TaskResponseMapper taskResponseMapper;

    public UserResponseMapper(TaskResponseMapper taskResponseMapper) {
        this.taskResponseMapper = taskResponseMapper;
    }

    @Override
    public UserResponseDTO apply(User user) {
        List<TaskResponseDTO> taskResponseDTOList = user.getTasks().stream().map(taskResponseMapper).collect(Collectors.toList());
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getRole(),
                taskResponseDTOList

        );
    }
}

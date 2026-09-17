package com.alin.task_management_project.dtos;

import com.alin.task_management_project.enums.Roles;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String name,
        Roles role,
        List<TaskResponseDTO> tasks
) {
}

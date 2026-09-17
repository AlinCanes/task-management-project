package com.alin.task_management_project.dtos;

import com.alin.task_management_project.entities.User;
import com.alin.task_management_project.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDTO(
        @NotBlank(message = "Short description is required")
        String shortDescription,
        @NotNull(message = "Priority is required")
        TaskPriority taskPriority

) {

}

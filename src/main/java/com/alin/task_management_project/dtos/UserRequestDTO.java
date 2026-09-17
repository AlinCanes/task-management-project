package com.alin.task_management_project.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Name is Required")
        String name
) {
}

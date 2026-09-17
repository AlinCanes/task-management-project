package com.alin.task_management_project.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterLoginRequestDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Password is required")
        String password
){
}

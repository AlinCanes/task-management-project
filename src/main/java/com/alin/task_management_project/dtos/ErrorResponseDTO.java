package com.alin.task_management_project.dtos;

public record ErrorResponseDTO(
        int status,
        String message
) {
}

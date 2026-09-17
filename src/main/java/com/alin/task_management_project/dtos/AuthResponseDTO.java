package com.alin.task_management_project.dtos;

import com.alin.task_management_project.enums.Roles;

import java.util.Date;

public record AuthResponseDTO(
        String name,
        String token,
        String role


) {
}

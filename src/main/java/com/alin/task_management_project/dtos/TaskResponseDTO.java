package com.alin.task_management_project.dtos;

import com.alin.task_management_project.enums.Active;
import com.alin.task_management_project.enums.TaskPriority;
import com.alin.task_management_project.enums.TaskStatus;

public record TaskResponseDTO(
        String shortDescription,
        String taskNumber,
        Long Id,
        TaskPriority taskPriority,
        TaskStatus taskStatus,
        Active active

) {
}

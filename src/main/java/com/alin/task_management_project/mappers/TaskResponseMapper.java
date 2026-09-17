package com.alin.task_management_project.mappers;

import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.entities.Task;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TaskResponseMapper implements Function<Task, TaskResponseDTO> {
    @Override
    public TaskResponseDTO apply(Task task) {
        return new TaskResponseDTO(
                task.getShortDescription(),
                task.getTaskNumber(),
                task.getId(),
                task.getTaskPriority(),
                task.getTaskStatus(),
                task.getActive()
        );
    }
}

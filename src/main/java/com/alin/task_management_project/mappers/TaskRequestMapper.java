package com.alin.task_management_project.mappers;

import com.alin.task_management_project.dtos.TaskRequestDTO;
import com.alin.task_management_project.entities.Task;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component

public class TaskRequestMapper implements Function<TaskRequestDTO, Task> {

    @Override
    public Task apply(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        task.setShortDescription(taskRequestDTO.shortDescription());
        task.setTaskPriority(taskRequestDTO.taskPriority());

        return task;
    }
}

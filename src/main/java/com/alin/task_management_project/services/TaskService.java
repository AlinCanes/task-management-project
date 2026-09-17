package com.alin.task_management_project.services;

import com.alin.task_management_project.dtos.TaskRequestDTO;
import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.entities.Task;
import com.alin.task_management_project.enums.Active;
import com.alin.task_management_project.enums.TaskStatus;
import com.alin.task_management_project.exceptions.TaskNotActiveException;
import com.alin.task_management_project.exceptions.TaskNotFoundException;
import com.alin.task_management_project.mappers.TaskRequestMapper;
import com.alin.task_management_project.mappers.TaskResponseMapper;
import com.alin.task_management_project.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskRequestMapper taskRequestMapper;
    private final TaskResponseMapper taskResponseMapper;

    public TaskService(TaskRepository taskRepository, TaskRequestMapper taskRequestMapper, TaskResponseMapper taskResponseMapper) {
        this.taskRepository = taskRepository;
        this.taskRequestMapper = taskRequestMapper;
        this.taskResponseMapper = taskResponseMapper;
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        Optional<Task> task = taskRepository.findById(id);

        if(task.isPresent()){
            return  task.get();
        }
        return  null;
    }

    public Task getTaskByNumber(String taskNumber){
        return taskRepository.findByTaskNumber(taskNumber).orElse(null);
    }
    public TaskResponseDTO createTask(TaskRequestDTO taskDTO){
        Task task = taskRequestMapper.apply(taskDTO);
        Task savedTask = taskRepository.save(task);
        savedTask.setTaskNumber(
                String.format("TASK-%05d", savedTask.getId()) //TASK-{task.id}
        );
        savedTask.setTaskStatus(TaskStatus.OPEN);

        taskRepository.save(savedTask);
        return taskResponseMapper.apply(savedTask);
    }

    public String closeCompleteTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if(task.getActive().equals(Active.FALSE)){
            throw new TaskNotActiveException(task.getTaskNumber());
        }

        task.setTaskStatus(TaskStatus.CLOSED_COMPLETE);
        task.setActive(Active.FALSE);

        taskRepository.save(task);
        return "Task " + task.getTaskNumber() + " was closed complete.";
    }

    public String closeIncompleteTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if(task.getActive().equals(Active.FALSE)){
            throw new TaskNotActiveException(task.getTaskNumber());
        }

        task.setTaskStatus(TaskStatus.CLOSED_INCOMPLETE);
        task.setActive(Active.FALSE);

        String taskNumber = task.getTaskNumber();

        taskRepository.save(task);
        return "Task " + taskNumber + " was closed incomplete.";
    }
}

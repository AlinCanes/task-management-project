package com.alin.task_management_project.controller;

import com.alin.task_management_project.dtos.TaskRequestDTO;
import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.entities.Task;
import com.alin.task_management_project.repositories.TaskRepository;
import com.alin.task_management_project.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @PostMapping("/createTask")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDTO));
    }

    @GetMapping("/{taskNumber}")
    public Task getTaskByNumber(@PathVariable String taskNumber){
        return taskService.getTaskByNumber(taskNumber);
    }

    @PutMapping("/completeTask/{taskId}")
    public ResponseEntity<String> closeCompleteTask(@PathVariable Long taskId){
        return  ResponseEntity.status(HttpStatus.OK).body(taskService.closeCompleteTask(taskId));
    }
    @PutMapping("/closeIncompleteTask/{taskId}")
    public ResponseEntity<String> closeIncompleteTask(@PathVariable Long taskId){
        return  ResponseEntity.status(HttpStatus.OK).body(taskService.closeIncompleteTask(taskId));
    }
}

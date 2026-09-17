package com.alin.task_management_project.controller;

import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.dtos.UserRequestDTO;
import com.alin.task_management_project.dtos.UserResponseDTO;
import com.alin.task_management_project.entities.Task;
import com.alin.task_management_project.entities.User;
import com.alin.task_management_project.repositories.TaskRepository;
import com.alin.task_management_project.repositories.UserRepository;
import com.alin.task_management_project.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


//    @PostMapping("/createUser")
//    public User createUser( @RequestBody User user){
//        return userRepository.save(user);
//    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(userId));
    }

    @PutMapping("/assignTask/{userId}/{taskId}")
    public ResponseEntity<UserResponseDTO> assignTask(@PathVariable Long userId, @PathVariable Long taskId){
       UserResponseDTO savedUser = userService.assignTask(userId,taskId);

        return ResponseEntity.ok(savedUser);

    }
    @GetMapping("/tasks/{userId}")
    public  ResponseEntity<List<TaskResponseDTO>> getUserTasks(@PathVariable Long userId){

        return ResponseEntity.ok(userService.getAllUserTasks(userId));
    }
}

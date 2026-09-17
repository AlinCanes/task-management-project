package com.alin.task_management_project.services;

import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.dtos.UserRequestDTO;
import com.alin.task_management_project.dtos.UserResponseDTO;
import com.alin.task_management_project.entities.Task;
import com.alin.task_management_project.entities.User;
import com.alin.task_management_project.exceptions.UserNotFoundException;
import com.alin.task_management_project.mappers.TaskResponseMapper;
import com.alin.task_management_project.mappers.UserResponseMapper;
import com.alin.task_management_project.mappers.UserRequestMapper;
import com.alin.task_management_project.repositories.TaskRepository;
import com.alin.task_management_project.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    private final UserRequestMapper userRequestMapper;
    private final TaskRepository taskRepository;
    private final TaskResponseMapper taskResponseMapper;


    public UserService(UserRepository userRepository, UserResponseMapper userResponseMapper, UserRequestMapper userRequestMapper, TaskRepository taskRepository, TaskResponseMapper taskResponseMapper) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
        this.userRequestMapper = userRequestMapper;
        this.taskRepository = taskRepository;

        this.taskResponseMapper = taskResponseMapper;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userRequestMapper.apply(userRequestDTO);
        User saverUser = userRepository.save(user);
        return userResponseMapper.apply(saverUser);
    }
//    public UserResponse createUser(UserRequest requestBody) {
//
//        User user = new User(requestBody);
//
//
//
//
//
//    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userResponseMapper)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id){
        //return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No user found with id=" + id));
        User user =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userResponseMapper.apply(user);
    }

//    public List<TaskResponseDTO> getAllUserTasks(Long userId){
//        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id=" + userId));
//
//        return taskRepository.findAll().stream().filter(task ->
//            task.getUser().getId().equals(user.getId()))
//                .map(taskResponseMapper)
//                .collect(Collectors.toList());
//    }
public List<TaskResponseDTO> getAllUserTasks(Long userId){
   // User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id=" + userId));
    if (!userRepository.existsById(userId)){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id=" + userId);
    }

    return taskRepository.findByUserId(userId).stream().map(taskResponseMapper).collect(Collectors.toList());

}

    public UserResponseDTO assignTask(Long userId, Long taskId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id=" + userId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id=" + taskId));

        task.setUser(user);
        taskRepository.save(task);

        return userResponseMapper.apply(user);
    }
}

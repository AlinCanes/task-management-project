package com.alin.task_management_project.services;

import com.alin.task_management_project.dtos.TaskRequestDTO;
import com.alin.task_management_project.dtos.TaskResponseDTO;
import com.alin.task_management_project.entities.Task;
import com.alin.task_management_project.enums.Active;
import com.alin.task_management_project.enums.TaskPriority;
import com.alin.task_management_project.enums.TaskStatus;
import com.alin.task_management_project.exceptions.TaskNotActiveException;
import com.alin.task_management_project.mappers.TaskRequestMapper;
import com.alin.task_management_project.mappers.TaskResponseMapper;
import com.alin.task_management_project.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TaskServiceTest {
    @Mock
    private  TaskRepository taskRepository;

    @Mock
    private TaskRequestMapper taskRequestMapper;

    @Mock
    private TaskResponseMapper taskResponseMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {

        task = new Task();
        task.setId(302L);
        task.setTaskNumber("TASK-00004");
        task.setActive(Active.TRUE);
        task.setTaskStatus(TaskStatus.OPEN);

    }

    @Test
    void closeCompleteTask() {


        when(taskRepository.findById(302L)).thenReturn(Optional.of(task));

        String result = taskService.closeCompleteTask(302L);

        assertEquals("Task " + task.getTaskNumber() + " was closed complete.", result);
        assertEquals(TaskStatus.CLOSED_COMPLETE, task.getTaskStatus());
        assertEquals(Active.FALSE, task.getActive());

        verify(taskRepository).save(task);

    }

    @Test
    void closeIncompleteTask_whenTaskIsAlreadyInactive_throwsException() {

        task.setActive(Active.FALSE);

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        assertThrows(TaskNotActiveException.class, () -> taskService.closeIncompleteTask(task.getId()));


        verify(taskRepository, never()).save(task);
    }

    @Test
    void createTask_assignsTaskNumberAndOpenStatus() {
        // given
        TaskRequestDTO request = new TaskRequestDTO(
                "Write tests",
                TaskPriority.HIGH
        );

        TaskResponseDTO expectedResponse = new TaskResponseDTO(
                "Write tests",
                "TASK-00302",
                302L,
                TaskPriority.HIGH,
                TaskStatus.OPEN,
                Active.TRUE
        );

        when(taskRequestMapper.apply(request)).thenReturn(task);

        // The first save gives the task its database-generated id.
        when(taskRepository.save(task)).thenReturn(task);

        when(taskResponseMapper.apply(task)).thenReturn(expectedResponse);

        // when
        TaskResponseDTO result = taskService.createTask(request);

        // then
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);

        verify(taskRepository, times(2)).save(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();

        assertEquals("TASK-00302", savedTask.getTaskNumber());
        assertEquals(TaskStatus.OPEN, savedTask.getTaskStatus());
        assertEquals(Active.TRUE, savedTask.getActive());
        assertEquals(expectedResponse, result);
    }
}

package com.alin.task_management_project.repositories;

import com.alin.task_management_project.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findByTaskNumber(String taskNumber);
     List<Task> findByUserId(Long userId);
}

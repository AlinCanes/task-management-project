package com.alin.task_management_project.entities;

import com.alin.task_management_project.enums.Active;
import com.alin.task_management_project.enums.TaskPriority;
import com.alin.task_management_project.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Table(name = "Tasks")
@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String taskNumber;
    private String shortDescription;
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @Enumerated(EnumType.STRING)
    private Active active = Active.TRUE;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;



}

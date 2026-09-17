package com.alin.task_management_project.exceptions;

public class TaskNotActiveException extends RuntimeException{
    public TaskNotActiveException(String taskNumber){
        super("Task with number=" + taskNumber + " is already closed");
    }
}

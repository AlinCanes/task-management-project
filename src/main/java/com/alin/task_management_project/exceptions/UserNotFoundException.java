package com.alin.task_management_project.exceptions;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException(Long id){
        super("User not found with id=" + id);
    }
}

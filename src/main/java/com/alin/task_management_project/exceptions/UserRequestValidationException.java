package com.alin.task_management_project.exceptions;

public class UserRequestValidationException extends RuntimeException{

    public UserRequestValidationException(String message){
        super(message);
    }
}

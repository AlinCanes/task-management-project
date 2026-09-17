package com.alin.task_management_project.exceptions;

public class UserByNameNotFoundException extends RuntimeException{


    public UserByNameNotFoundException(String name){
        super("User with name=" + name + " not found.");
    }
}

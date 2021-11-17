package com.task.examstrial1.exception;

public class UserNotFoundException extends  RuntimeException{


    public UserNotFoundException(String message) {
        super(message);
    }
}

package com.task.examstrial1.exception;

public class UserAlreadyExists extends AbstractUserExceptions{
    String  userId;
    public UserAlreadyExists(String message , String userId) {
        super(message);
        this.userId=userId;
    }
}

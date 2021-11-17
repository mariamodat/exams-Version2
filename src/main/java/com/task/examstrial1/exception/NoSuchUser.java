package com.task.examstrial1.exception;

public class NoSuchUser extends AbstractUserExceptions{
    String userId;
    public NoSuchUser(String message,  String userId) {
        super(message);
        this.userId=userId;
    }
}

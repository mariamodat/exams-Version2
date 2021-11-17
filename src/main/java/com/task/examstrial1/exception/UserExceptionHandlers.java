package com.task.examstrial1.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
@RequiredArgsConstructor
public class UserExceptionHandlers {

    private final UserExceptionMessages userExceptionMessages;
    private static final ErrorInfo DEFAULT_ERROR = new ErrorInfo(-1, 400, new HashMap(){{put(Lang.EN,"General Error");}});

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Object> handleAlreadyExist(AbstractUserExceptions exceptions ){
        ErrorInfo err = userExceptionMessages.getExceptionMessages().getOrDefault(exceptions.getClass().getSimpleName(), DEFAULT_ERROR);
        return new ResponseEntity(err, new HttpHeaders(),err.getStatusCode());
    }

    @ExceptionHandler(NoSuchUser.class)
    public ResponseEntity<Object> handleRemoveUnValidUser(AbstractUserExceptions exceptions){
        ErrorInfo errorInfo = userExceptionMessages.getExceptionMessages().getOrDefault(exceptions.getClass().getSimpleName(), DEFAULT_ERROR);
        return new ResponseEntity<>(errorInfo,new HttpHeaders(),errorInfo.getStatusCode());
    }

}

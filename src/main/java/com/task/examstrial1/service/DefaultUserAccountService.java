package com.task.examstrial1.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultUserAccountService {
    private final UserService userService;

    public DefaultUserAccountService(UserService userService) {
        this.userService = userService;
    }
}

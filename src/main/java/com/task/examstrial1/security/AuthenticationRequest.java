package com.task.examstrial1.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
@AllArgsConstructor
@Getter
public class AuthenticationRequest {

    private String username;
    private String password;
}

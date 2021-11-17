package com.task.examstrial1.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class SecurityUser {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<SecurityRole> securityRoles;
    private String password;


}

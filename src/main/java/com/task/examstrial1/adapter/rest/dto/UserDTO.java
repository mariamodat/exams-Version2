package com.task.examstrial1.adapter.rest.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class UserDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}

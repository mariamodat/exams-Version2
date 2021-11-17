package com.task.examstrial1.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SecurityRole {
    private Long id;
    private String roleName;
    private String description;
}

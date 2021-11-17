package com.task.examstrial1.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Exam {
    private Long id;
    private String title;
    private String duration;
    private String question;
}

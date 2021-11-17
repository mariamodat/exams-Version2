package com.task.examstrial1.adapter.rest.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class ExamDTO {
    private Long id;
    private String title;
    private String duration;
    private String question;
}

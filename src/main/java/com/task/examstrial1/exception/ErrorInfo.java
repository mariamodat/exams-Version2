package com.task.examstrial1.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorInfo {
    private int code ;
    private int statusCode;
    private Map<Lang,String> msg;
}

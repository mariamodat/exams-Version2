package com.task.examstrial1.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "user.errors")
@Data
public class UserExceptionMessages {
    private Map<String ,ErrorInfo> exceptionMessages;
}

package com.task.examstrial1.adapter.rest.controller;

import com.task.examstrial1.adapter.rest.dto.ExamDTO;
import com.task.examstrial1.model.Exam;
import com.task.examstrial1.service.ExamService;
import com.task.examstrial1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final UserService userService;
    private final ExamService examService;

    public ExamController(UserService userService, ExamService examService) {
        this.userService = userService;
        this.examService = examService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello world";
    }


    @PostMapping("/new")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ExamDTO> postNewExam(@RequestBody ExamDTO examDTO){
        Optional<Exam> savedExam = examService.save(toExamModel(examDTO));
        ExamDTO examDTO2 = savedExam.map(this::toDTO).get();
        return new ResponseEntity<>(examDTO2, HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ExamDTO showingExamByTitle(@PathVariable String title){
        Exam exam = examService.findByTitle(title).orElseThrow(NoSuchElementException::new);
        return toDTO(exam);
    }


//    @PreAuthorize("hasAuthority('admin')")
    @Secured("admin")
    @GetMapping
    public List<ExamDTO> showingAllExams(){
        return examService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private Exam toExamModel(ExamDTO examDTO){
        return Exam.builder()
                .id(examDTO.getId())
                .title(examDTO.getTitle())
                .duration(examDTO.getDuration())
                .question(examDTO.getQuestion())
                .build();
    }

    private ExamDTO toDTO(Exam examDTO){
        return ExamDTO.builder()
                .id(examDTO.getId())
                .title(examDTO.getTitle())
                .duration(examDTO.getDuration())
                .question(examDTO.getQuestion())
                .build();
    }
}

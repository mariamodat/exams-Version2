package com.task.examstrial1.service;

import com.task.examstrial1.adapter.repository.ExamRepository;
import com.task.examstrial1.entity.ExamEntity;
import com.task.examstrial1.model.Exam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Optional<Exam> save(Exam exam){
        return examRepository.save(toEntity(exam));
    }
    public Optional<Exam> findByTitle(String title){
        return examRepository.getByTitle(title);
    }

    public List<Exam> findAll(){
        return examRepository.getAllExams();
    }



    private ExamEntity toEntity(Exam exam){
        return ExamEntity.builder()
                .id(exam.getId())
                .duration(exam.getDuration())
                .question(exam.getQuestion())
                .title(exam.getTitle())
                .build();
    }
}

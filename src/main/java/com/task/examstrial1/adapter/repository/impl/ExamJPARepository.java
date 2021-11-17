package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.adapter.repository.ExamRepository;
import com.task.examstrial1.entity.ExamEntity;
import com.task.examstrial1.model.Exam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExamJPARepository implements ExamRepository {

    private final ExamJPASpringRepoImpl examJPASpringRepo;

    public ExamJPARepository(ExamJPASpringRepoImpl examJPASpringRepo) {
        this.examJPASpringRepo = examJPASpringRepo;
    }

    @Override
    public Optional<Exam> save(ExamEntity exam) {
        return Optional.ofNullable(toModel(examJPASpringRepo.save(exam)));
    }

    @Override
    public Optional<Exam> getByTitle(String title) {
        return examJPASpringRepo.findByTitle(title).map(this::toModel);
    }

    @Override
    public List<Exam> getAllExams() {
        List<ExamEntity> all = examJPASpringRepo.findAll();
        return all.stream().map(this::toModel).collect(Collectors.toList());
    }


    private Exam toModel(ExamEntity exam){
        return Exam.builder()
                .id(exam.getId())
                .duration(exam.getDuration())
                .question(exam.getQuestion())
                .title(exam.getTitle())
                .build();
    }
}

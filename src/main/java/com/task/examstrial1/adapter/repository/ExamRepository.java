package com.task.examstrial1.adapter.repository;

import com.task.examstrial1.entity.ExamEntity;
import com.task.examstrial1.model.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamRepository {
    Optional<Exam> save(ExamEntity exam);

    Optional<Exam> getByTitle(String title);
    List<Exam> getAllExams();
}

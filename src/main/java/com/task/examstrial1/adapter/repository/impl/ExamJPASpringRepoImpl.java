package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.entity.ExamEntity;
import com.task.examstrial1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamJPASpringRepoImpl extends JpaRepository<ExamEntity,Long> {
    Optional<ExamEntity> findByTitle(String title);
}

package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPASpringRepoImpl extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}

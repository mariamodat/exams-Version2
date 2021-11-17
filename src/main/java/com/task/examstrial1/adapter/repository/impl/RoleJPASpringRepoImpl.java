package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJPASpringRepoImpl extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findRoleEntityByRoleName(String roleName);
}

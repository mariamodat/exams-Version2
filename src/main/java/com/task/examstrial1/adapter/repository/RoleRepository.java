package com.task.examstrial1.adapter.repository;

import com.task.examstrial1.entity.RoleEntity;
import com.task.examstrial1.model.SecurityRole;

import java.util.Optional;

public interface RoleRepository {
    Optional<SecurityRole> findByRoleName(String roleName);
}

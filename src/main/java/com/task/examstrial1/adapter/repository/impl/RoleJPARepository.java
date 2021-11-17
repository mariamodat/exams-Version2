package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.adapter.repository.RoleRepository;
import com.task.examstrial1.entity.RoleEntity;
import com.task.examstrial1.model.SecurityRole;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class RoleJPARepository implements RoleRepository {

    private final RoleJPASpringRepoImpl roleJPASpringRepo;

    public RoleJPARepository(RoleJPASpringRepoImpl roleJPASpringRepo) {
        this.roleJPASpringRepo = roleJPASpringRepo;
    }

    @Override
    public Optional<SecurityRole> findByRoleName(String roleName) {
        return roleJPASpringRepo.findRoleEntityByRoleName(roleName).map(this::toSecRole);
    }

    private SecurityRole toSecRole(RoleEntity userEntity) {

        return SecurityRole.builder()
                .roleName(userEntity.getRoleName())
                .description(userEntity.getDescription())
                .id(userEntity.getId())
                .build();
    }
}

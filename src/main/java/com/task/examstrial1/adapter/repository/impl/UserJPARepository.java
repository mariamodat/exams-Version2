package com.task.examstrial1.adapter.repository.impl;

import com.task.examstrial1.adapter.repository.UserRepository;
import com.task.examstrial1.entity.RoleEntity;
import com.task.examstrial1.entity.UserEntity;
import com.task.examstrial1.model.SecurityRole;
import com.task.examstrial1.model.SecurityUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserJPARepository  implements UserRepository {
    private final UserJPASpringRepoImpl userJPASpringRepo;

    public UserJPARepository(UserJPASpringRepoImpl userJPASpringRepo) {
        this.userJPASpringRepo = userJPASpringRepo;
    }

    @Override
    public SecurityUser save(SecurityUser user) {
        UserEntity save = userJPASpringRepo.save(toUserEntity(user));

        return toSecurityUser(save);
    }

    @Override
    public Optional<SecurityUser> getUserById(Long id) {
        return Optional.ofNullable(toSecurityUser(userJPASpringRepo.getById(id)));
    }

    private SecurityUser toSecurityUser(UserEntity use) {
        return SecurityUser.builder()
                .email(use.getEmail())
                .id(use.getId())
                .firstname(use.getFirstname())
                .lastname(use.getLastname())
                .password(use.getPassword())
                .username(use.getUsername())
                .securityRoles(use.getRoles()
                        .stream()
                        .map(this::toSecRole)
                        .collect(Collectors.toList()))
                .build();
    }

    private SecurityRole toSecRole(RoleEntity userEntity) {

        return SecurityRole.builder()
                .roleName(userEntity.getRoleName())
                .description(userEntity.getDescription())
                .id(userEntity.getId())
                .build();
    }

    @Override
    public Optional<SecurityUser> getUserByUsername(String username) {
        Optional<UserEntity> byUsername = userJPASpringRepo.findByUsername(username);
        return byUsername.map(this::toSecurityUser);
    }

    @Override
    public List<SecurityUser> getAllUsers() {
        return userJPASpringRepo.findAll().stream()
                .map(this::toSecurityUser)
                .collect(Collectors.toList());
    }


    private UserEntity toUserEntity(SecurityUser use){
        return UserEntity.builder()
                .email(use.getEmail())
                .id(use.getId())
                .firstname(use.getFirstname())
                .lastname(use.getLastname())
                .password(use.getPassword())
                .username(use.getUsername())
                .roles(use.getSecurityRoles()
                        .stream()
                        .map(this::toRoleEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    private RoleEntity toRoleEntity(SecurityRole securityRole){
        return RoleEntity.builder()
                .roleName(securityRole.getRoleName())
                .description(securityRole.getDescription())
                .id(securityRole.getId())
                .build();
    }
}

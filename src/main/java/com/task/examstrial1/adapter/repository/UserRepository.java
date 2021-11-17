package com.task.examstrial1.adapter.repository;

import com.task.examstrial1.entity.UserEntity;
import com.task.examstrial1.model.SecurityUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    SecurityUser save(SecurityUser user);
    Optional<SecurityUser> getUserById(Long id);
    Optional<SecurityUser> getUserByUsername(String username);
    List<SecurityUser> getAllUsers();

}

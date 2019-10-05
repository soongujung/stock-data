package com.api.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuthVo, Long> {
    Optional<UserAuthVo> findByUsername(String username);
}

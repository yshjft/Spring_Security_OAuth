package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dao;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteUserById(Long userId);
}

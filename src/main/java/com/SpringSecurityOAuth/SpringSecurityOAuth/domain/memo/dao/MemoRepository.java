package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Page<Memo> findByUser(User user, Pageable pageable);

    // fetch join으로
    @Deprecated
    Optional<Memo> findByIdAndUserId(Long id, Long userId);

    @Query("select m from Memo m join fetch m.user where m.id = :id")
    Optional<Memo> findByIdWithUser(@Param("id") Long id);
}

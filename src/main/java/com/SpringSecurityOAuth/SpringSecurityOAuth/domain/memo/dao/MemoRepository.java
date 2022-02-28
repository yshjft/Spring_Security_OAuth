package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}

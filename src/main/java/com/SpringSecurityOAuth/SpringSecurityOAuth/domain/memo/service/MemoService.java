package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao.MemoRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoRequestDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoResponseDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final UserService userService;
    private final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDto writeMemo(MemoRequestDto memoRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto)authentication.getPrincipal();

        String email = principal.getEmail();
        User user = userService.getUserByEmail(email);

        Memo memo = Memo.builder()
                .memo(memoRequestDto.getMemo())
                .user(user)
                .build();

        memoRepository.save(memo);

        return new MemoResponseDto(memo.getId());
    }
}

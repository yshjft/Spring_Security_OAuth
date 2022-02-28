package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao.MemoRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoResponseDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemoServiceTest {
    @InjectMocks
    MemoService memoService;
    @Mock
    UserService userService;
    @Mock
    MemoRepository memoRepository;
    @Mock
    SecurityContext securityContext;

    @BeforeEach
    void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, "");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void 메모_작성() {
        // given
        when(userService.getUserByEmail("test@test.com")).thenReturn(user);
        when(memoRepository.save(any())).thenReturn(memo);

        // when
        MemoResponseDto memoResponseDto = memoService.writeMemo(memoRequestDto);

        // then
        verify(userService).getUserByEmail("test@test.com");
        verify(memoRepository).save(any());

        assertThat(memoResponseDto).isNotNull();
    }


}
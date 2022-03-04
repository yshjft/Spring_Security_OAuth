package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenStoreService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dao.MemoRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dao.UserRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.Role;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.Optional;

import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.user;
import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.userDto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    TokenService tokenService;
    @Mock
    TokenStoreService tokenStoreService;
    @Mock
    UserRepository userRepository;
    @Mock
    MemoRepository memoRepository;
    @Mock
    SecurityContext securityContext;

    @BeforeEach
    void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, "abc");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void 회원_정보_조회() {
        // given
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        // when
        userService.getUserInfo();

        // then
        verify(userRepository).findByEmail(any());
    }

    @Test
    void 회원_정보_조회_실패() {
        // given
        when(userRepository.findByEmail(any())).thenThrow(new UserNotFoundException());

        try{
            // then
            userService.getUserInfo();
        }catch (UserNotFoundException e) {
            // when
            verify(userRepository).findByEmail(any());
        }
    }

    @Test
    void 회원_탈퇴() {
        // given
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(tokenService.getExpirationInMS(any())).thenReturn(1L);

        // when
        userService.deleteUser();

        // then
        verify(userRepository).findByEmail(any());
        verify(memoRepository).bulkDeleteByUser(user.getId());
        verify(userRepository).deleteUserById(user.getId());
        verify(tokenStoreService).setBlackList("abc", 1L);
        verify(tokenStoreService).deleteRefreshToken(user.getEmail());
    }


    @Test
    void 회원_탈퇴_존재하지_않는_사용자() {
        //when
        when(userRepository.findByEmail(any())).thenThrow(new UserNotFoundException());
        when(tokenService.getExpirationInMS(any())).thenReturn(1L);

        // given
        userService.deleteUser();

        // then
        verify(userRepository).findByEmail(any());
        verify(memoRepository, never()).bulkDeleteByUser(user.getId());
        verify(userRepository, never()).deleteUserById(user.getId());
        verify(tokenStoreService).setBlackList("abc", 1L);
        verify(tokenStoreService).deleteRefreshToken(user.getEmail());
    }
}
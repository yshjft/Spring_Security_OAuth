package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
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
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Optional;

import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.user;
import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.userDto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    SecurityContext securityContext;

    @BeforeEach
    void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto, "");
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
}
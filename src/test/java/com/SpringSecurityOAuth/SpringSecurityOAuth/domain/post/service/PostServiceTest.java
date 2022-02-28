package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dao.PostRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.domain.Post;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto.PostRequestDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto.PostResponseDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.Role;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.SpringSecurityOAuth.SpringSecurityOAuth.domain.TestData.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    PostService postService;
    @Mock
    UserService userService;
    @Mock
    PostRepository postRepository;
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
        when(postRepository.save(any())).thenReturn(post);

        // when
        PostResponseDto postResponseDto = postService.writeMemo(postRequestDto);

        // then
        verify(userService).getUserByEmail("test@test.com");
        verify(postRepository).save(any());

        assertThat(postResponseDto).isNotNull();
    }


}
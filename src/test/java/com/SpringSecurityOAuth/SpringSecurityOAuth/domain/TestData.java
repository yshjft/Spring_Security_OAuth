package com.SpringSecurityOAuth.SpringSecurityOAuth.domain;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.domain.Post;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto.PostRequestDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.Role;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;

public class TestData {
    public static UserDto userDto = UserDto.builder()
            .email("test@test.com")
            .build();

    public static User user = User.builder()
            .id(1L)
            .name("tester")
            .email("test@test.com")
            .picture("image_url")
            .role(Role.USER)
            .build();

    public static PostRequestDto postRequestDto = PostRequestDto.builder()
            .memo("jerry")
            .build();

    public static Post post = Post.builder()
            .id(2L)
            .memo("jerry")
            .user(user)
            .build();

}

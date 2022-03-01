package com.SpringSecurityOAuth.SpringSecurityOAuth.domain;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.domain.Memo;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto.MemoWriteDto;
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

    public static User user2 = User.builder()
            .id(3L)
            .name("tester2")
            .email("tes2t@test.com")
            .picture("image_url2")
            .role(Role.USER)
            .build();

    public static MemoWriteDto memoWriteDto = MemoWriteDto.builder()
            .memo("jerry")
            .build();

    public static MemoWriteDto memoUpdateDto = MemoWriteDto.builder()
            .memo("jerry2222")
            .build();

    public static Memo memo = Memo.builder()
            .id(2L)
            .memo("jerry")
            .user(user)
            .build();

    public static Memo memo2 = Memo.builder()
            .id(3L)
            .memo("test test test")
            .user(user2)
            .build();
}

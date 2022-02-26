package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dao.UserRepository;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto.UserInfoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoDto getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto)authentication.getPrincipal();
        String email = principal.getEmail();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());


        return UserInfoDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profile_image(user.getPicture())
                .build();
    }
}

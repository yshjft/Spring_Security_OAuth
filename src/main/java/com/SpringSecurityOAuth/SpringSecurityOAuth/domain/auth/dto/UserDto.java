package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class UserDto {
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDto(String email, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.authorities = authorities;
    }
}

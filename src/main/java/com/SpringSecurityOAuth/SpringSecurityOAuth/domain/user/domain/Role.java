package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER");
    private final String role;
}

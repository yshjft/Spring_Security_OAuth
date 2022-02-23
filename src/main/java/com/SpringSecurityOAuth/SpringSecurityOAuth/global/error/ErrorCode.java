package com.SpringSecurityOAuth.SpringSecurityOAuth.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), "E40101", "unauthorized access"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40102", "token expired. refresh it");

    private final int status;
    private final String code;
    private final String message;
}

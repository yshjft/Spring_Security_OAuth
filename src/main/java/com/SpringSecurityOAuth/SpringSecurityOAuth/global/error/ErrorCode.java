package com.SpringSecurityOAuth.SpringSecurityOAuth.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED.value(), "E40101", "unauthorized access"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40102", "token expired. refresh it."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40103", "wrong token. login again;"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "E40104", "invalid refresh token. login again.");


    private final int status;
    private final String code;
    private final String message;
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util;

import org.springframework.http.ResponseCookie;

public class RefreshTokenResponseCookie {
    public static ResponseCookie of(String refreshTokenKey, String refreshToken, boolean httpOnly, boolean secure, int maxAge) {
        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenKey, refreshToken)
                .httpOnly(httpOnly)
                .secure(secure)
                .maxAge(maxAge)
                .build();

        return responseCookie;
    }
}

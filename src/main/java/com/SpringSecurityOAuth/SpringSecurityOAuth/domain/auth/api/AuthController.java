package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.TokenDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.AuthService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util.RefreshTokenResponseCookie;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${jwt.refresh-token}") private String refreshTokenKey;

    @GetMapping("/token/refresh")
    public ResponseEntity<ResponseDto> refreshAccessToken() {
        TokenDto tokenDto = authService.refreshAccessToken();

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("token refreshed")
                .result(tokenDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<ResponseDto> signOut() {
        authService.signOut();

        ResponseCookie responseCookie = RefreshTokenResponseCookie.of(refreshTokenKey, null, true, false, 0);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("logout success")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(responseDto);
    }
}

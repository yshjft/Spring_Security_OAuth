package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.TokenDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;

    @GetMapping("/token/refresh")
    public ResponseEntity<String> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();

        String accessToken = tokenService.createAccessToken(userDto);
        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("token refreshed")
                .result(new TokenDto(accessToken))
                .build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}

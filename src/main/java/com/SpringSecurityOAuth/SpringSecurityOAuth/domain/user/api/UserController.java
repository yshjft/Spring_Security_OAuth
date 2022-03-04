package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util.RefreshTokenResponseCookie;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto.UserInfoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Value("${jwt.refresh-token}") private String refreshTokenKey;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto> getUserInfo() {
        UserInfoDto userInfoDto = userService.getUserInfo();
        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("get user info success")
                .result(userInfoDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/info")
    public ResponseEntity<ResponseDto> deleteUser() {
        userService.deleteUser();

        ResponseCookie responseCookie = RefreshTokenResponseCookie.of(refreshTokenKey, null, true, false, 0);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("delete user successfully. bye.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(responseDto);
    }
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto.UserInfoDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.service.UserService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
}

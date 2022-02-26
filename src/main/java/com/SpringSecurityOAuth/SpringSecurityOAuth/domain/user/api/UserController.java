package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto.TestDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/greeting")
    public ResponseEntity<ResponseDto> greetingUser() {
        TestDto testDto = TestDto.builder()
                .greeting("Hi!")
                .build();

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("greeting success!")
                .result(testDto)
                .build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}

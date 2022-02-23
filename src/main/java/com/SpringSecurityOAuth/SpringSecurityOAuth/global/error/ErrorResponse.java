package com.SpringSecurityOAuth.SpringSecurityOAuth.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private List errors;

    @Builder
    public ErrorResponse(int status, String code, String message, List errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    // 단일 에러
    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .errors(new ArrayList<>())
                .build();
    }

    // 복수 에러
    // public static ErrorResponse of(ErrorCode errorCode, Exception e) {}
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ResponseDto<T> {
    private int status;
    private String message;
    private T result;

    @Builder
    public ResponseDto(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}

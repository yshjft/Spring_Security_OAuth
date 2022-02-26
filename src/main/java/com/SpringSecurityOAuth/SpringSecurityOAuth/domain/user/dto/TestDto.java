package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestDto {
    private String greeting;
    private String tmp;

    @Builder
    public TestDto(String greeting, String tmp) {
        this.greeting = greeting;
        this.tmp = tmp;
    }
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private Long id;

    public MemoResponseDto(Long id) {
        this.id = id;
    }
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemoIdDto {
    private Long id;

    public MemoIdDto(Long id) {
        this.id = id;
    }
}

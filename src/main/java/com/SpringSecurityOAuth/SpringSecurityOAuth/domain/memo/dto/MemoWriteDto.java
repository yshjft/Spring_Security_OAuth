package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemoWriteDto {
    @NotNull
    @Size(min = 1, max = 300)
    private String memo;

    @Builder
    public MemoWriteDto(String memo) {
        this.memo = memo;
    }
}

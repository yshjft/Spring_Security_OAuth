package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    @NotNull
    @Size(min = 1, max = 10)
    private String memo;

    @Builder
    public PostRequestDto(String memo) {
        this.memo = memo;
    }
}

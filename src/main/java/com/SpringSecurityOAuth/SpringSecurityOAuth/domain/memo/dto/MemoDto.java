package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.memo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoDto {
    private Long id;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MemoDto(Long id, String memo, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memo = memo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

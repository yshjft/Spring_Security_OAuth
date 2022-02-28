package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    
    public PostResponseDto(Long id) {
        this.id = id;
    }
}

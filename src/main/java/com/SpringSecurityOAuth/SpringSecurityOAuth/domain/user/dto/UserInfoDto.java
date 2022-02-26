package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String name;
    private String email;
    private String profile_image;

    @Builder
    public UserInfoDto(String name, String email, String profile_image) {
        this.name = name;
        this.email = email;
        this.profile_image = profile_image;
    }
}

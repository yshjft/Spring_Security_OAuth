package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.Role;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {
    private String name;
    private String email;
    private String picture;
    private Map<String, Object> attributes;
    private String nameAttributeKey;


    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String usernameAttributeName, Map<String, Object> attributes) {
        if("kakao".equals(registrationId)) {
            return ofKakao(usernameAttributeName, attributes);
        }
        return ofGoogle(usernameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String usernameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String)kakaoProfile.get("nickname"))
                .email((String)kakaoAccount.get("email"))
                .picture((String)kakaoProfile.get("thumbnail_image_url"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}

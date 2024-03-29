package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.TokenDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final TokenStoreService tokenStoreService;

    public TokenDto refreshAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) authentication.getPrincipal();

        String savedRefreshToken = tokenStoreService.getRefreshToken(principal.getEmail());
        String refreshedAccessToken = tokenService.refreshAccessToken(savedRefreshToken, principal);

        return new TokenDto(refreshedAccessToken);
    }

    public void signOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) authentication.getPrincipal();
        String accessToken = (String)authentication.getCredentials();

        // blackList
        Long expirationInMS = tokenService.getExpirationInMS(accessToken);
        tokenStoreService.setBlackList(accessToken, expirationInMS);

        // refresh token 제거
        tokenStoreService.deleteRefreshToken(principal.getEmail());
    }
}

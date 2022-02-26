package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.TokenDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenStoreService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.global.common.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final TokenStoreService jwtStoreService;

    @Value("${jwt.refresh-token-valid-second}") private int refreshTokenPeriodInSec;
    @Value("${jwt.refresh-token}") private String refreshTokenKey;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String)attributes.get("email");

        String accessToken = tokenService.createAccessToken(
                UserDto.builder()
                        .email(email)
                        .authorities(oAuth2User.getAuthorities())
                        .build());
        String refreshToken = tokenService.createRefreshToken();

        jwtStoreService.setRefreshToken(email, refreshToken, refreshTokenPeriodInSec);

        makeResponse(response, accessToken, refreshToken);
    }

    private void makeResponse(HttpServletResponse response, String accessToken, String refreshToken) throws IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 응답 객체
        response.getWriter().write(objectMapper.writeValueAsString(
                ResponseDto.builder()
                .status(HttpServletResponse.SC_OK)
                .message("login success")
                .result(new TokenDto(accessToken))
                .build()
        ));

        // 쿠키
        // 배포시 secure 설정
        ResponseCookie responseCookie = RefreshTokenResponseCookie.of(refreshTokenKey, refreshToken, true, false, refreshTokenPeriodInSec);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }
}

package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final TokenStoreService tokenStoreService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String token = tokenService.resolveAccessToken(httpServletRequest);
        boolean ignoreExpired = httpServletRequest.getRequestURI().equals("/api/auth/token/refresh");

        if(hasText(token)
                && tokenService.validateToken(token, httpServletRequest, ignoreExpired)
                && tokenStoreService.getBlackList(token) == null
        ) {
            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}

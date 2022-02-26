package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements InitializingBean {
    private static final String USER_EMAIL_KEY = "USER_EMAIL";
    private static final String AUTHORITIES_KEY = "AUTHORITIES";

    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.token-valid-second}") private long accessTokenPeriodInSec;
    @Value("${jwt.refresh-token-valid-second}") private long refreshTokenPeriodInSec;
    @Value("${jwt.refresh-token}") private String refreshTokenKey;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String refreshAccessToken(String savedRefreshToken, UserDto userDto) {
        String refreshToken = resolveRefreshToken();

        if(!hasText(refreshToken) || !hasText(savedRefreshToken) || !refreshToken.equals(savedRefreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        return createAccessToken(userDto);
    }

    private String resolveRefreshToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Cookie[] cookies = servletRequestAttributes.getRequest().getCookies();
        String refreshToken = null;

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(refreshTokenKey)){
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        return refreshToken;
    }

    public String createAccessToken(UserDto userDto){
        String email = userDto.getEmail();
        String authorities = userDto.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + (this.accessTokenPeriodInSec*1000));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject("ACCESS_TOKEN")
                .claim(USER_EMAIL_KEY, email)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(now)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(){
        Date now = new Date();
        Date expiredTime = new Date(now.getTime() + (this.refreshTokenPeriodInSec*1000));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject("REFRESH_TOKEN")
                .setIssuedAt(now)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, HttpServletRequest request, boolean ignoreExpired) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e) {
            if(ignoreExpired) return true;
            request.setAttribute("exception", "EXPIRED_TOKEN");
        }catch (JwtException | IllegalArgumentException e) {
            request.setAttribute("exception", "WRONG_TOKEN");
        }
        return false;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        String email = (String)claims.get(USER_EMAIL_KEY);
        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDto userDto = UserDto.builder()
                .email(email)
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userDto, accessToken, authorities);
    }

    public Long getExpirationInMS(String accessToken) {
        Date expiration = parseClaims(accessToken).getExpiration();
        Long now = new Date().getTime();

        return (expiration.getTime() - now);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

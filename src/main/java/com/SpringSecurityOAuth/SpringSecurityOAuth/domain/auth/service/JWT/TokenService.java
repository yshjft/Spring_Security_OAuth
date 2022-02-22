package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.JWT;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.dto.UserDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenService implements InitializingBean {
    private static final String USER_EMAIL_KEY = "USER_EMAIL";
    private static final String AUTHORITIES_KEY = "AUTHORITIES";

    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.token-valid-second}") private long accessTokenPeriodInSec;
    @Value("${jwt.refresh-token-valid-second}") private long refreshTokenPeriodInSec;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
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

//    public boolean validateToken() {}

//    public Authentication getAuthentication(// DTO) {}
}

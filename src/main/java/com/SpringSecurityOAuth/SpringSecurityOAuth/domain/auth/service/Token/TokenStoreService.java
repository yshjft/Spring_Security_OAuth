package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenStoreService implements InitializingBean {
    private final RedisTemplate redisTemplate;
    private final String refreshTokenNameSpace = "SECURITY_JWT_REFRESH_TOKEN:";
    private final String blackListTokenNameSpace = "SECURITY_JWT_BLACK_LIST:";
    private ValueOperations<String, String> valueOperations;

    @Override
    public void afterPropertiesSet() throws Exception {
        valueOperations = redisTemplate.opsForValue();
    }

    // get refreshToken
    public String getRefreshToken(String email) {
        String key = refreshTokenNameSpace+email;
        return valueOperations.get(key);
    }

    // set refreshToken
    public void setRefreshToken(String email, String refreshToken, long duration) {
        String key = refreshTokenNameSpace+email;
        Duration expiredDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, refreshToken, expiredDuration);
    }

    // delete refreshToken

    // set blacklist
    public void setBlackList(String accessToken, Long expirationInMS) {
        if(expirationInMS <= 0) return;

        String key = blackListTokenNameSpace+accessToken;
        Duration expiredDuration = Duration.ofMillis(expirationInMS);

        valueOperations.set(key, "INVALID_ACCESS_TOKEN", expiredDuration);
    }

    // get blacklist
    public String getBlackList(String accessToken) {
        return valueOperations.get(blackListTokenNameSpace+accessToken);
    }
}

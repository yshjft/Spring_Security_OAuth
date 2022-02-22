package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.JWT;

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
public class JwtStoreService implements InitializingBean {
    private final RedisTemplate redisTemplate;
    private final String refreshTokenNameSpace = "SECURITY_JWT_REFRESH_TOKEN:";
    private final String blackListTokenNameSpace = "SECURITY_JWT_BLACK_LIST:";
    private ValueOperations<String, String> valueOperations;

    @Override
    public void afterPropertiesSet() throws Exception {
        valueOperations = redisTemplate.opsForValue();
    }

    // get refreshToken

    // set refreshToken
    public void setRefreshToken(String email, String refreshToken, long duration) {
        String key = refreshTokenNameSpace+email;
        Duration expiredDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, refreshToken, expiredDuration);
    }

    // delete refreshToken

}

package com.SpringSecurityOAuth.SpringSecurityOAuth.global.config;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.handler.OAuthSuccessHandler;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.OAuth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuthService oAuthService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/","/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint().userService(oAuthService); // 소셜 로그인 성공 시 후속 조치를 진행할 User Service 인터페이스의 구현체를 등록
    }
}

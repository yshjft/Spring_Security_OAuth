package com.SpringSecurityOAuth.SpringSecurityOAuth.global.config;

import antlr.TokenStream;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.Token.TokenStoreService;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util.CustomAuthenticationEntryPoint;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util.JwtAuthenticationFilter;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.util.OAuthSuccessHandler;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.service.OAuth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuthService oAuthService;
    private final TokenService tokenService;
    private final TokenStoreService tokenStoreService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
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
                .antMatchers("/test/**", "/favicon.ico", "/api/auth/exception/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint().userService(oAuthService); // 소셜 로그인 성공 시 후속 조치를 진행할 User Service 인터페이스의 구현체를 등록

        http.addFilterBefore(new JwtAuthenticationFilter(tokenService, tokenStoreService), UsernamePasswordAuthenticationFilter.class);
    }
}

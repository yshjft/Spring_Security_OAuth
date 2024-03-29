package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.api;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.ExpiredTokenException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.UnAuthorizedAccessException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.WrongTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/auth/exception")
public class AuthExceptionController {
    @GetMapping("/unauthorized")
    public void unAuthorizedException() {
        throw new UnAuthorizedAccessException();
    }

    @GetMapping("/token/expired")
    public void expiredTokenException() {
        throw new ExpiredTokenException();
    }

    @GetMapping("/token/wrong")
    public void wrongTokenException() {
        throw new WrongTokenException();
    }
}

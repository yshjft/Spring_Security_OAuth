package com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/greeting")
    public ResponseEntity<String> greetingUser() {
        return new ResponseEntity("Hi!", HttpStatus.OK);
    }
}

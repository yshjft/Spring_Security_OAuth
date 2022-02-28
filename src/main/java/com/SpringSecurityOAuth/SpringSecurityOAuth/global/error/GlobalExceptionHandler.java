package com.SpringSecurityOAuth.SpringSecurityOAuth.global.error;

import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.ExpiredTokenException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.InvalidRefreshTokenException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.UnAuthorizedAccessException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.auth.exception.WrongTokenException;
import com.SpringSecurityOAuth.SpringSecurityOAuth.domain.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_REQUEST, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedAccessException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED_ACCESS);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handlerExpiredTokenException(ExpiredTokenException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.EXPIRED_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<ErrorResponse> handlerWrongTokenException(WrongTokenException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.WRONG_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_REFRESH_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

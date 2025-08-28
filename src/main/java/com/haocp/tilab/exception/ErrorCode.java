package com.haocp.tilab.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    USERNAME_INCORRECT(401, "Username is incorrect", HttpStatus.UNAUTHORIZED),
    PASSWORD_INCORRECT(401, "Password is incorrect", HttpStatus.UNAUTHORIZED),
    HAVE_NOT_LOGIN(403, "You can't access to this action", HttpStatus.FORBIDDEN),;

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}

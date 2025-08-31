package com.haocp.tilab.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    USERNAME_INCORRECT(401, "Username is incorrect", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(401, "Password is incorrect", HttpStatus.NOT_FOUND),
    HAVE_NOT_LOGIN(403, "You can't access to this action", HttpStatus.FORBIDDEN),
    THERE_NO_MEMBERSHIP(404, "There is no membership", HttpStatus.NOT_FOUND),
    ACCOUNT_BANNED(403, "Your account is banned", HttpStatus.FORBIDDEN),
    STAFF_NOT_FOUND(404, "Staff not found", HttpStatus.NOT_FOUND),;

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}

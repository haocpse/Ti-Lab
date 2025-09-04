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
    STAFF_NOT_FOUND(404, "Staff not found", HttpStatus.NOT_FOUND),
    BAG_NOT_FOUND(404, "Bag not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXIST(404, "User not exist", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(404, "Customer not found", HttpStatus.NOT_FOUND),
    COUPON_NOT_EXIST(404, "Coupon not exist", HttpStatus.NOT_FOUND),
    ORDER_DETAIL_NOT_FOUND(404, "Order detail not found", HttpStatus.NOT_FOUND),
    NO_PAYMENT_SUITABLE(404, "There no payment is suitable", HttpStatus.NOT_FOUND),
    EXCEED_MAXIMUM_QUANTITY(400, "You can only buy up to %d items", HttpStatus.BAD_REQUEST),
    FILE_IMAGE_NULL(400, "File image is null", HttpStatus.BAD_REQUEST),;

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }

}

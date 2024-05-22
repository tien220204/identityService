package com.tien.identity.service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(1001, "User existed"),
    USERNAME_INVALID(1003, "Invalid username"),
    PASSWORD_INVALID(1003, "Invalid password"),
    FIELD_INVALID(1004, "Invalid field"),
    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "wrong login information"),
    UNAUTHORIZED(401,"No right to access this resource");
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

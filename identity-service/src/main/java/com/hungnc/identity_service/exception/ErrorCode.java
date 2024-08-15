package com.hungnc.identity_service.exception;

public enum ErrorCode {

    NOT_FOUND(1404, "Not Found"),
    UNCATEGORIZED_EXCEPTION(1405, "Uncategorized"),
    UNAUTHENTICATED(1406, "Unauthenticated"),
    TOKEN_INVALID(1407, "Token Invalid"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

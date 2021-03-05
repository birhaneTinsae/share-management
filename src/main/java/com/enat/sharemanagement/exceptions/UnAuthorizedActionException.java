package com.enat.sharemanagement.exceptions;

public class UnAuthorizedActionException extends  RuntimeException {
    private final String message;
    public UnAuthorizedActionException(String msg) {
        this.message = msg;
    }
    @Override
    public String getMessage() {
        return message;
    }
}

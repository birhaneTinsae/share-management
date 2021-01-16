package com.enat.sharemanagement.exceptions;

public class PasswordMisMatchException extends RuntimeException {
   private String message;

    public PasswordMisMatchException(String message) {
        super(message);

    }
}

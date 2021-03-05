package com.enat.sharemanagement.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PasswordMisMatchException extends RuntimeException {
    private final String message;

    public PasswordMisMatchException(String message) {
        super(message);
        this.message = message;
    }
}

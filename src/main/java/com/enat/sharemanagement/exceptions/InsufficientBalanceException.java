package com.enat.sharemanagement.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class InsufficientBalanceException extends RuntimeException {
    private final String message;

    public InsufficientBalanceException(String message) {
        this.message = message;
    }
}

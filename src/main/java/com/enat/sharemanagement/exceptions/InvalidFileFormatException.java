package com.enat.sharemanagement.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class InvalidFileFormatException extends RuntimeException {
    private final String message;
    public InvalidFileFormatException(String message) {
        this.message=message;
    }
}

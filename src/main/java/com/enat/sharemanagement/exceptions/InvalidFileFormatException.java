package com.enat.sharemanagement.exceptions;

import lombok.Data;

@Data
public class InvalidFileFormatException extends RuntimeException {
    private String message;
    public InvalidFileFormatException(String message) {
        this.message=message;
    }
}

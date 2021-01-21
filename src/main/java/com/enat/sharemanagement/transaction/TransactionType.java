package com.enat.sharemanagement.transaction;

import lombok.Getter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Getter
public enum TransactionType {
    NEW('N'), ADDITION('A'), REVERSAL('R'), REMAINING_PAYMENT('X'), PAYMENT('P'),SERVICE_CHARGE('S');

    private final char transactionType;

    TransactionType(char p) {
        this.transactionType = p;
    }
}


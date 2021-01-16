package com.enat.sharemanagement.share;

import lombok.Getter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Getter
public enum TransactionType {
    NEW('N'), ADDITION('A'), REVERSAL('R'), REMAINING_PAYMENT('X'), PAYMENT('P');

    private final char transactionType;

    TransactionType(char p) {
        this.transactionType = p;
    }
}


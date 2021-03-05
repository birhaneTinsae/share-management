package com.enat.sharemanagement.transaction;

import lombok.Getter;

@Getter
public enum TransactionType {
    NEW('N'), ADDITION('A'), REVERSAL('R'),
    REMAINING_PAYMENT('X'),
    PAYMENT('P'), SERVICE_CHARGE('S');

    private final char transactionType;

    TransactionType(char p) {
        this.transactionType = p;
    }
}


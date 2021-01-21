package com.enat.sharemanagement.transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, Character> {

    @Override
    public Character convertToDatabaseColumn(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return transactionType.getTransactionType();
    }

    @Override
    public TransactionType convertToEntityAttribute(Character character) {
        if (character == null) {
            return null;
        }

        return Stream.of(TransactionType.values())
                .filter(p -> p.getTransactionType() == character)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

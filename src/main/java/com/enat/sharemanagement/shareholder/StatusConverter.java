/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enat.sharemanagement.shareholder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 *
 * @author birhane
 */
@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Character> {

    @Override
    public Character convertToDatabaseColumn(Status x) {
        if (x == null) {
            return null;
        }
        return x.getStatus();
    }

    @Override
    public Status convertToEntityAttribute(Character y) {
        if (y == null) {
            return null;
        }

        return Stream.of(Status.values())
                .filter(p -> p.getStatus() == y)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

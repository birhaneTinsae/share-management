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
public class SexConverter implements AttributeConverter<Sex, Character> {

    @Override
    public Character convertToDatabaseColumn(Sex x) {
        if (x == null) {
            return null;
        }
        return x.getSex();
    }

    @Override
    public Sex convertToEntityAttribute(Character y) {
        if (y == null) {
            return null;
        }

        return Stream.of(Sex.values())
                .filter(p -> p.getSex()== y)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

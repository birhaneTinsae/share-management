package com.enat.sharemanagement.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null || propertyName.equals("id"))
                .toArray(String[]::new);
    }

    public static BigDecimal getDailyIncrement(double noOfDays, LocalDate effectiveDate) {
        long dates = ChronoUnit.DAYS.between(LocalDate.of(effectiveDate.getYear(),
                Month.JUNE,
                30),
                effectiveDate);
        return BigDecimal.valueOf(noOfDays / dates);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static <S, T> T dtoMapper(S source, Class<T> targetClass, ModelMapper modelMapper) {
        return modelMapper.map(source, targetClass);
    }
}

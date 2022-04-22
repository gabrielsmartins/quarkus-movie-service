package br.gasmartins.movies.infra.persistence.entity.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {


    @Override
    public String convert(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public LocalDate unconvert(String value) {
        return LocalDate.parse(value);
    }
}

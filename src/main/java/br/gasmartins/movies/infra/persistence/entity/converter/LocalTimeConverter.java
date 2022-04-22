package br.gasmartins.movies.infra.persistence.entity.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements DynamoDBTypeConverter<String, LocalTime> {


    @Override
    public String convert(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public LocalTime unconvert(String value) {
        return LocalTime.parse(value);
    }
}

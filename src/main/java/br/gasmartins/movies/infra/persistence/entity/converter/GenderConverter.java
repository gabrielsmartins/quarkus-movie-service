package br.gasmartins.movies.infra.persistence.entity.converter;

import br.gasmartins.movies.domain.enums.Gender;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class GenderConverter implements DynamoDBTypeConverter<String, Gender> {

    @Override
    public String convert(Gender gender) {
        if(gender == null)
            return null;
        return gender.getPrefix().toString();
    }

    @Override
    public Gender unconvert(String value) {
        if(value == null)
            return null;
        return Gender.fromPrefix(value.charAt(0));
    }
}

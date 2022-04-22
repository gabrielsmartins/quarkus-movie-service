package br.gasmartins.movies.infra.persistence.entity.converter;

import br.gasmartins.movies.domain.enums.Genre;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class GenreConverter implements DynamoDBTypeConverter<String, Genre> {


    @Override
    public String convert(Genre genre) {
        if(genre == null)
            return null;
        return genre.getDescription();
    }

    @Override
    public Genre unconvert(String value) {
        if(value == null)
            return null;
        return Genre.fromDescription(value);
    }
}

package br.gasmartins.movies.infra.persistence.entity;

import br.gasmartins.movies.domain.enums.Gender;
import br.gasmartins.movies.infra.persistence.entity.converter.GenderConverter;
import br.gasmartins.movies.infra.persistence.entity.converter.LocalDateConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
@DynamoDBDocument
public class ActorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @DynamoDBAttribute(attributeName="Name")
    private String name;

    @DynamoDBAttribute(attributeName="Role")
    private String role;

    @DynamoDBAttribute(attributeName="Dob")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    private LocalDate dateOfBirthday;

    @DynamoDBAttribute(attributeName="Gender")
    @DynamoDBTypeConverted(converter = GenderConverter.class)
    private Gender gender;


}

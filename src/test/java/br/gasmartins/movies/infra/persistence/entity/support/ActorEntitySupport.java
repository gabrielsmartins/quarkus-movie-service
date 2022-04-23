package br.gasmartins.movies.infra.persistence.entity.support;

import br.gasmartins.movies.domain.enums.Gender;
import br.gasmartins.movies.infra.persistence.entity.ActorEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorEntitySupport {

    public static ActorEntity.ActorEntityBuilder defaultActorEntity(){
        return ActorEntity.builder()
                        .withName("Sylvester Stallone")
                        .withGender(Gender.MALE)
                        .withRole("Rocky Balboa")
                        .withDateOfBirthday(LocalDate.parse("1976-07-06"));
    }
}

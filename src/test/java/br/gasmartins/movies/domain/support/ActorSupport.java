package br.gasmartins.movies.domain.support;

import br.gasmartins.movies.domain.Actor;
import br.gasmartins.movies.domain.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorSupport {

    public static Actor.ActorBuilder defaultActor(){
        return Actor.builder()
                        .withName("Sylvester Stallone")
                        .withGender(Gender.MALE)
                        .withRole("Rocky Balboa")
                        .withDateOfBirthday(LocalDate.parse("1976-07-06"));
    }
}

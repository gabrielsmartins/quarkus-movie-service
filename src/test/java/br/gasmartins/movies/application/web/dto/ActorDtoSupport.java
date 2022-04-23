package br.gasmartins.movies.application.web.dto;

import br.gasmartins.movies.domain.enums.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorDtoSupport {

    public static ActorDto.ActorDtoBuilder defaultActorDto(){
        return ActorDto.builder()
                        .withName("Sylvester Stallone")
                        .withGender(Gender.MALE.getPrefix().toString())
                        .withRole("Rocky Balboa")
                        .withDateOfBirthday(LocalDate.parse("1976-07-06"));
    }
}

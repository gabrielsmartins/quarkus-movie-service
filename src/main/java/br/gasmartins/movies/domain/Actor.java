package br.gasmartins.movies.domain;

import br.gasmartins.movies.domain.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class Actor {

    private String name;
    private String role;
    private LocalDate dateOfBirthday;
    private Gender gender;

}

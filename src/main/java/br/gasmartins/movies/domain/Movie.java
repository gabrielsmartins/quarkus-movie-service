package br.gasmartins.movies.domain;

import br.gasmartins.movies.domain.enums.Genre;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class Movie {

    private UUID id;
    private String name;
    private Genre genre;
    private Integer year;
    private String directedBy;
    private LocalTime duration;

    @Singular
    private List<Actor> actors;


}

package br.gasmartins.movies.application.web.dto;

import br.gasmartins.movies.domain.enums.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDtoSupport {

    public static MovieDto.MovieDtoBuilder defaultMovieDto(){
        return MovieDto.builder()
                .withId(UUID.randomUUID())
                .withName("Rocky Balboa")
                .withGenre(Genre.ACTION.getDescription())
                .withYear(2006)
                .withDirectedBy("Sylvester Stallone")
                .withDuration(LocalTime.of(1, 30));
    }
}

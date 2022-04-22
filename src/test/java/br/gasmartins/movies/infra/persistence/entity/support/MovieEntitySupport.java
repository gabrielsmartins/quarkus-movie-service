package br.gasmartins.movies.infra.persistence.entity.support;

import br.gasmartins.movies.infra.persistence.entity.MovieEntity;
import br.gasmartins.movies.domain.enums.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieEntitySupport {

    public static MovieEntity.MovieEntityBuilder defaultMovieEntity(){
        return MovieEntity.builder()
                .withId(UUID.randomUUID())
                .withName("John Wick")
                .withGenre(Genre.ACTION)
                .withYear(2018)
                .withDuration(LocalTime.of(1, 30));
    }
}

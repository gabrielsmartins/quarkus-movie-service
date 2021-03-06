package br.gasmartins.movies.domain.support;

import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.enums.Genre;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieSupport {

    public static Movie.MovieBuilder defaultMovie(){
        return Movie.builder()
                .withId(UUID.randomUUID())
                .withName("Rocky Balboa")
                .withGenre(Genre.ACTION)
                .withYear(2006)
                .withDirectedBy("Sylvester Stallone")
                .withDuration(LocalTime.of(1, 30));
    }
}

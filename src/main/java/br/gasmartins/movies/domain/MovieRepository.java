package br.gasmartins.movies.domain;

import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.Page;
import br.gasmartins.movies.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {
    Movie save(Movie movie);
    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByName(String name, Pageable pageable);
    Optional<Movie> findById(UUID id);
    void delete(UUID id);


}

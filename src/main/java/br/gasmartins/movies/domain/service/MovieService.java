package br.gasmartins.movies.domain.service;

import br.gasmartins.movies.domain.Movie;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import java.util.Optional;
import java.util.UUID;

public interface MovieService {

    Movie save(Movie movie);
    PaginatedScanList<Movie> findAll();
    PaginatedQueryList<Movie> findByName(String name);
    Optional<Movie> findById(UUID id);
    void delete(UUID id);




}

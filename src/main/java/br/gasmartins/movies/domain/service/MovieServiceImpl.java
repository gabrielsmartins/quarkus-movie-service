package br.gasmartins.movies.domain.service;

import br.gasmartins.movies.infra.persistence.repository.MovieRepository;
import br.gasmartins.movies.domain.Movie;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Inject
    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movie save(Movie movie) {
        return this.repository.save(movie);
    }

    @Override
    public PaginatedScanList<Movie> findAll() {
        return this.repository.findAll();
    }

    @Override
    public PaginatedQueryList<Movie> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public Optional<Movie> findById(UUID id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        this.repository.delete(id);
    }

}

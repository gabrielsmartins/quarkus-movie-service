package br.gasmartins.movies.domain.service;

import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.Page;
import br.gasmartins.movies.domain.Pageable;
import br.gasmartins.movies.domain.MovieRepository;

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
    public Page<Movie> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<Movie> findByName(String name, Pageable pageable) {
        return this.repository.findByName(name, pageable);
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

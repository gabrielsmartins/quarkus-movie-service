package br.gasmartins.movies.infra.persistence.repository.mapper;

import br.gasmartins.movies.domain.Actor;
import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.infra.persistence.entity.ActorEntity;
import br.gasmartins.movies.infra.persistence.entity.MovieEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieDynamoRepositoryMapper {

    private final ActorDynamoRepositoryMapper actorMapper;

    @Inject
    public MovieDynamoRepositoryMapper(ActorDynamoRepositoryMapper actorMapper) {
        this.actorMapper = actorMapper;
    }

    public MovieEntity mapToEntity(Movie movie) {
        var mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<Movie, MovieEntity>() {
            @Override
            protected void configure() {
                using((Converter<List<Actor>, List<ActorEntity>>) mappingContext -> mappingContext.getSource().stream().map(actorMapper::mapToEntity).collect(Collectors.toList())).map(this.source.getActors(), this.destination.getActors());
            }
        });
        return mapper.map(movie, MovieEntity.class );
    }

    public Movie mapToDomain(MovieEntity movieEntity) {
        var mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<MovieEntity, Movie>() {
            @Override
            protected void configure() {
                using((Converter<List<ActorEntity>, List<Actor>>) mappingContext -> mappingContext.getSource().stream().map(actorMapper::mapToDomain).collect(Collectors.toList())).map(this.source.getActors(), this.destination.getActors());
            }
        });
        return mapper.map(movieEntity, Movie.class);
    }


}

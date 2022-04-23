package br.gasmartins.movies.application.web.mapper;

import br.gasmartins.movies.application.web.dto.ActorDto;
import br.gasmartins.movies.application.web.dto.MovieDto;
import br.gasmartins.movies.domain.Actor;
import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.enums.Genre;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovieControllerMapper {

    private final ActorControllerMapper actorMapper;

    @Inject
    public MovieControllerMapper(ActorControllerMapper actorMapper) {
        this.actorMapper = actorMapper;
    }

    public MovieDto mapToDto(Movie movie){
        var mapper = new ModelMapper();
        return mapper.map(movie, MovieDto.class);
    }

    public Movie mapToDomain(MovieDto movieDto){
        var mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<MovieDto, Movie>() {
            @Override
            protected void configure() {
                using((Converter<List<ActorDto>, List<Actor>>) mappingContext -> mappingContext.getSource()
                                                                                               .stream()
                                                                                               .map(actorMapper::mapToDomain)
                                                                                               .collect(Collectors.toList()))
                        .map(this.source.getActors(), this.destination.getActors());
                using((Converter<String, Genre>) mappingContext -> Genre.fromDescription(mappingContext.getSource())).map(this.source.getGenre(), this.destination.getGenre());
            }
        });
        return mapper.map(movieDto, Movie.class);
    }
}

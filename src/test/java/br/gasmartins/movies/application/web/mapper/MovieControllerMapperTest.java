package br.gasmartins.movies.application.web.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.movies.application.web.dto.ActorDtoSupport.defaultActorDto;
import static br.gasmartins.movies.application.web.dto.MovieDtoSupport.defaultMovieDto;
import static br.gasmartins.movies.domain.support.ActorSupport.defaultActor;
import static br.gasmartins.movies.domain.support.MovieSupport.defaultMovie;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieControllerMapperTest {

    private MovieControllerMapper mapper;

    @BeforeEach
    public void setup(){
        var actorMapper = new ActorControllerMapper();
        this.mapper = new MovieControllerMapper(actorMapper);
    }

    @Test
    @DisplayName("Given Movie When Map Then Return Movie Dto")
    public void givenMovieWhenMapThenReturnMovieDto(){
        var actor = defaultActor().build();
        var movie = defaultMovie()
                .withActor(actor)
                .build();
        var movieDto = this.mapper.mapToDto(movie);
        assertThat(movieDto).hasNoNullFieldsOrProperties();
        assertThat(movieDto.getId()).isEqualTo(movie.getId());
        assertThat(movieDto.getName()).isEqualTo(movie.getName());
        assertThat(movieDto.getGenre()).isEqualToIgnoringCase(movie.getGenre().getDescription());
        assertThat(movieDto.getYear()).isEqualTo(movie.getYear());
        assertThat(movieDto.getDuration()).isEqualTo(movie.getDuration());
        assertThat(movieDto.getDirectedBy()).isEqualTo(movie.getDirectedBy());
        assertThat(movieDto.getActors().size()).isEqualTo(movie.getActors().size());
    }

    @Test
    @DisplayName("Given Movie Dto When Map Then Return Movie")
    public void givenMovieDtoWhenMapThenReturnMovie(){
        var actorDto = defaultActorDto().build();
        var movieDto = defaultMovieDto()
                                        .withActor(actorDto)
                                        .build();
        var movie = this.mapper.mapToDomain(movieDto);
        assertThat(movie).hasNoNullFieldsOrProperties();
        assertThat(movie.getId()).isEqualTo(movieDto.getId());
        assertThat(movie.getName()).isEqualTo(movieDto.getName());
        assertThat(movie.getGenre().getDescription()).isEqualTo(movieDto.getGenre());
        assertThat(movie.getYear()).isEqualTo(movieDto.getYear());
        assertThat(movie.getDuration()).isEqualTo(movieDto.getDuration());
        assertThat(movie.getDirectedBy()).isEqualTo(movieDto.getDirectedBy());
        assertThat(movie.getActors().size()).isEqualTo(movieDto.getActors().size());
    }
}

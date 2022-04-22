package br.gasmartins.movies.infra.persistence.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.movies.infra.persistence.entity.support.MovieEntitySupport.defaultMovieEntity;
import static br.gasmartins.movies.domain.support.MovieSupport.defaultMovie;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieDynamoRepositoryMapperTest {

    private MovieDynamoRepositoryMapper mapper;

    @BeforeEach
    public void setup(){
        var actorMapper = new ActorDynamoRepositoryMapper();
        this.mapper = new MovieDynamoRepositoryMapper(actorMapper);
    }

    @Test
    @DisplayName("Given Movie When Map Then Return Movie Entity")
    public void givenMovieWhenMapThenReturnMovieEntity(){
        var movie = defaultMovie().build();
        var movieEntity = this.mapper.mapToEntity(movie);
        assertThat(movieEntity).hasNoNullFieldsOrProperties();
        assertThat(movieEntity.getId()).isEqualTo(movie.getId());
        assertThat(movieEntity.getName()).isEqualTo(movie.getName());
        assertThat(movieEntity.getGenre()).isEqualTo(movie.getGenre().getDescription());
        assertThat(movieEntity.getYear()).isEqualTo(movie.getYear());
        assertThat(movieEntity.getDuration()).isEqualTo(movie.getDuration());
        assertThat(movieEntity.getActors().size()).isEqualTo(movie.getActors().size());
    }

    @Test
    @DisplayName("Given Movie Entity When Map Then Return Movie")
    public void givenMovieEntityWhenMapThenReturnMovie(){
        var movieEntity = defaultMovieEntity().build();
        var movie = this.mapper.mapToDomain(movieEntity);
        assertThat(movie).hasNoNullFieldsOrProperties();
        assertThat(movie.getId()).isEqualTo(movieEntity.getId());
        assertThat(movie.getName()).isEqualTo(movieEntity.getName());
        assertThat(movie.getGenre().getDescription()).isEqualTo(movieEntity.getGenre());
        assertThat(movie.getYear()).isEqualTo(movieEntity.getYear());
        assertThat(movie.getDuration()).isEqualTo(movieEntity.getDuration());
        assertThat(movie.getActors().size()).isEqualTo(movieEntity.getActors().size());
    }
}

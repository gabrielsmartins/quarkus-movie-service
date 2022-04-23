package br.gasmartins.movies.domain.service;

import br.gasmartins.movies.domain.*;
import br.gasmartins.movies.domain.support.MovieSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    private MovieService service;
    private MovieRepository repository;

    @BeforeEach
    public void setup(){
        this.repository = mock(MovieRepository.class);
        this.service = new MovieServiceImpl(repository);
    }

    @Test
    @DisplayName("Given Movie When Save Then Return Saved Movie")
    public void givenMovieWhenSaveThenReturnSavedMovie(){
        var movie = MovieSupport.defaultMovie().build();

        when(this.repository.save(any(Movie.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        var savedMovie = this.service.save(movie);
        assertThat(savedMovie).isNotNull();
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Return Movie")
    public void givenMovieIdWhenExistsThenReturnMovie(){
        var movie = MovieSupport.defaultMovie().build();

        when(this.repository.findById(any(UUID.class))).thenReturn(Optional.of(movie));

        var optionalMovie = this.service.findById(movie.getId());
        assertThat(optionalMovie).isPresent();
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Delete Movie")
    public void givenMovieIdWhenExistsThenDeleteMovie(){
        var movie = MovieSupport.defaultMovie().build();

        this.service.delete(movie.getId());
        verify(this.repository, times(1)).delete(any(UUID.class));
    }

    @Test
    @DisplayName("Given Movie List When Exists Then Return Movie List")
    public void givenMovieListWhenExistsThenReturnMovieList(){
        var movie = MovieSupport.defaultMovie().build();
        var pageable = PageRequest.of(0, 30);
        var pageMock = new PageImpl<>(List.of(movie), pageable, 1);
        when(this.repository.findAll(any(Pageable.class))).thenReturn(pageMock);
        var page = this.service.findAll(pageable);
        assertThat(page).isNotNull();
    }

    @Test
    @DisplayName("Given Movie Name When Exists Then Return Movie List")
    public void givenMovieNameWhenExistsThenReturnMovieList(){
        var movie = MovieSupport.defaultMovie().build();
        var pageable = PageRequest.of(0, 30);
        var pageMock = new PageImpl<>(List.of(movie), pageable, 1);
        when(this.repository.findByName(anyString(), any(Pageable.class))).thenReturn(pageMock);
        var page = this.service.findByName("Foo", pageable);
        assertThat(page).isNotNull();
    }

}

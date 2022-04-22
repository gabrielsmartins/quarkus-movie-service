package br.gasmartins.movies.domain.service;

import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.support.MovieSupport;
import br.gasmartins.movies.infra.persistence.repository.MovieRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

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
        Assertions.assertThat(savedMovie).isNotNull();
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Return Movie")
    public void givenMovieIdWhenExistsThenReturnMovie(){
        var movie = MovieSupport.defaultMovie().build();

        when(this.repository.findById(any(UUID.class))).thenReturn(Optional.of(movie));

        var optionalMovie = this.service.findById(movie.getId());
        Assertions.assertThat(optionalMovie).isPresent();
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
        var paginatedScanList = mock(PaginatedScanList.class);
        when(this.repository.findAll()).thenReturn(paginatedScanList);
        var page = this.service.findAll();
        Assertions.assertThat(page).isNotNull();
    }

    @Test
    @DisplayName("Given Movie Name When Exists Then Return Movie List")
    public void givenMovieNameWhenExistsThenReturnMovieList(){
        var paginatedQueryList = mock(PaginatedQueryList.class);
        when(this.repository.findByName(anyString())).thenReturn(paginatedQueryList);
        var page = this.service.findByName("Foo");
        Assertions.assertThat(page).isNotNull();
    }
}

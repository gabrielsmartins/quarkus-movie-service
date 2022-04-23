package br.gasmartins.movies.application.web;

import br.gasmartins.movies.domain.Movie;
import br.gasmartins.movies.domain.PageImpl;
import br.gasmartins.movies.domain.PageRequest;
import br.gasmartins.movies.domain.Pageable;
import br.gasmartins.movies.domain.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.gasmartins.movies.application.web.dto.ActorDtoSupport.defaultActorDto;
import static br.gasmartins.movies.application.web.dto.MovieDtoSupport.defaultMovieDto;
import static br.gasmartins.movies.domain.support.MovieSupport.defaultMovie;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
public class MovieControllerTest {

    @Inject
    private ObjectMapper mapper;

    @InjectMock
    private MovieService service;


    @Test
    @DisplayName("Given Movie When Save Then Return Saved Movie")
    public void givenMovieWhenSaveThenReturnSavedMovie() throws JsonProcessingException {
        var actorDto = defaultActorDto().build();
        var movieDto = defaultMovieDto()
                                    .withActor(actorDto)
                                    .build();
        var body = mapper.writeValueAsString(movieDto);

        when(this.service.save(any(Movie.class))).thenReturn(defaultMovie().build());

        given()
          .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body(body)
                .post("/movies/v1")
          .then()
             .statusCode(201)
             .body(containsString(movieDto.getName()));
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Return Movie")
    public void givenMovieIdWhenExistsThenReturnMovie(){
        var movie = defaultMovie().build();

        when(this.service.findById(any(UUID.class))).thenReturn(Optional.of(movie));

        given()
                .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .pathParam("movie_id", movie.getId())
                .get("/movies/v1/{movie_id}")
                .then()
                .statusCode(200)
                .body(containsString(movie.getName()));
    }

    @Test
    @DisplayName("Given Movie Id When Exists Then Delete Movie")
    public void givenMovieIdWhenExistsThenDeleteMovie(){
        given()
                .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .pathParam("movie_id", UUID.randomUUID())
                .delete("/movies/v1/{movie_id}")
                .then()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    @DisplayName("Given Movie List When Exists Then Return Movie List")
    public void givenMovieListWhenExistsThenReturnMovieList(){
        var movie = defaultMovie().build();

       var pageMock = new PageImpl<>(List.of(movie), PageRequest.of(0, 30), 1);
        when(this.service.findAll(any(Pageable.class))).thenReturn(pageMock);

        given()
                .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .queryParam("page", 0)
                .queryParam("page_size", 10)
                .get("/movies/v1")
                .then()
                .statusCode(206)
                .body(containsString(movie.getName()));
    }

    @Test
    @DisplayName("Given Movie Name When Exists Then Return Movie List")
    public void givenMovieNameWhenExistsThenReturnMovieList(){
        var movie = defaultMovie().build();

        var pageMock = new PageImpl<>(List.of(movie), PageRequest.of(0, 30), 1);
        when(this.service.findByName(anyString(), any(Pageable.class))).thenReturn(pageMock);

        given()
                .when()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .queryParam("name", movie.getName())
                .queryParam("page", 0)
                .queryParam("page_size", 10)
                .get("/movies/v1")
                .then()
                .statusCode(206)
                .body(containsString(movie.getName()));
    }


}
package br.gasmartins.movies.application.web;

import br.gasmartins.movies.application.web.dto.MovieDto;
import br.gasmartins.movies.application.web.mapper.MovieControllerMapper;
import br.gasmartins.movies.domain.exception.MovieNotFoundException;
import br.gasmartins.movies.domain.service.MovieService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Path("/movies/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class MovieController {

    private final MovieService service;
    private final MovieControllerMapper mapper;

    @Inject
    public MovieController(MovieService service, MovieControllerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @POST
    public MovieDto create(MovieDto movieDto) {
        log.info("Creating new movie: {}", kv("movie",movieDto));
        log.info("Mapping movie: {}", kv("movie",movieDto));
        var movie = this.mapper.mapToDomain(movieDto);
        log.info("Saving movie: {}", kv("movie", movie));
        var savedMovie = this.service.save(movie);
        log.info("Movie was saved successfully: {}", savedMovie);
        return this.mapper.mapToDto(savedMovie);
    }

    @PUT
    @Path("{id}")
    public MovieDto update(@PathParam("id") UUID id, MovieDto movieDto) {
        log.info("Searching for movie by id: {}", kv("id", id));
        var existingMovie = this.service.findById(id)
                                       .orElseThrow(() -> new MovieNotFoundException(String.format("Movie for ID %s does not exist", id)));
        var movie = this.mapper.mapToDomain(movieDto);
        movie.setId(existingMovie.getId());
        log.info("Updating movie: {}", kv("movie", movie));
        var updatedMovie = this.service.save(movie);
        return this.mapper.mapToDto(updatedMovie);
    }

    @GET
    @Path("{id}")
    public MovieDto findById(@PathParam("id") UUID id) {
        log.info("Searching for movie by id: {}", kv("id", id));
        var movie = this.service.findById(id)
                                       .orElseThrow(() -> new MovieNotFoundException(String.format("Movie for ID %s does not exist", id)));
        log.info("Movie found: {}", kv("movie", movie));
        return this.mapper.mapToDto(movie);
    }

    @GET
    public Response findAll() {
        log.info("Searching for movies ...");
        var paginatedScanList = this.service.findAll();
        if(!paginatedScanList.isEmpty()){
            var movies = paginatedScanList.stream()
                                                        .map(this.mapper::mapToDto)
                                                        .collect(Collectors.toList());
            log.info("Movies found: {}", kv("movies", movies));
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(movies).build();
        }
        log.warn("No movies found");
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") UUID id) {
        log.info("Deleting movie by id: {}", kv("id", id));
        this.service.delete(id);
        log.info("Movie was deleted successfully ...");
        return Response.ok().build();
    }

}

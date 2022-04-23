package br.gasmartins.movies.application.web;

import br.gasmartins.movies.application.web.dto.MovieDto;
import br.gasmartins.movies.application.web.mapper.MovieControllerMapper;
import br.gasmartins.movies.domain.PageRequest;
import br.gasmartins.movies.domain.exception.MovieNotFoundException;
import br.gasmartins.movies.domain.service.MovieService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    public Response create(@Valid MovieDto movieDto, @Context UriInfo uriInfo) {
        log.info("Creating new movie: {}", kv("movie",movieDto));
        log.info("Mapping movie: {}", kv("movie",movieDto));
        var movie = this.mapper.mapToDomain(movieDto);
        log.info("Saving movie: {}", kv("movie", movie));
        var savedMovie = this.service.save(movie);
        log.info("Movie was saved successfully: {}", savedMovie);
        var mappedMovie = this.mapper.mapToDto(savedMovie);
        var uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(mappedMovie.getId().toString());
        return Response.created(uriBuilder.build()).entity(mappedMovie).build();
    }

    @PUT
    @Path("/{id}")
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
    @Path("/{id}")
    public MovieDto findById(@PathParam("id") UUID id) {
        log.info("Searching for movie by id: {}", kv("id", id));
        var movie = this.service.findById(id)
                                       .orElseThrow(() -> new MovieNotFoundException(String.format("Movie for ID %s does not exist", id)));
        log.info("Movie found: {}", kv("movie", movie));
        return this.mapper.mapToDto(movie);
    }

    @GET
    public Response findByCriteria(@QueryParam("name") String name, @DefaultValue("0") @QueryParam("page") Integer pageNumber, @DefaultValue("30") @QueryParam("page_size") Integer pageSize){
        if(name == null || name.isEmpty()){
            return findAll(pageNumber, pageSize);
        }
        return findByName(name, pageNumber, pageSize);
    }

    private Response findAll(Integer pageNumber, Integer pageSize) {
        log.info("Searching for movies ...");
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        var page = this.service.findAll(pageable);
        if(!page.isEmpty()){
            var movies = page.getContent().stream()
                                                        .map(this.mapper::mapToDto)
                                                        .collect(Collectors.toList());
            log.info("Movies found: {}", kv("movies", movies));
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(movies).build();
        }
        log.warn("No movies found");
        return Response.noContent().build();
    }

    private Response findByName(String name, Integer pageNumber, Integer pageSize) {
        log.info("Searching for movies ...");
        var pageable = PageRequest.of(pageNumber, pageSize);
        var page = this.service.findByName(name, pageable);
        if(!page.isEmpty()){
            var movies = page.getContent().stream()
                                           .map(this.mapper::mapToDto)
                                           .collect(Collectors.toList());
            log.info("Movies found: {}", kv("movies", movies));
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(movies).build();
        }
        log.warn("No movies found");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        log.info("Deleting movie by id: {}", kv("id", id));
        this.service.delete(id);
        log.info("Movie was deleted successfully ...");
        return Response.ok().build();
    }

}

package br.gasmartins.movies.application.web;

import br.gasmartins.movies.application.web.dto.ErrorDto;
import br.gasmartins.movies.domain.exception.MovieNotFoundException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Provider
@Slf4j
public class ExceptionHandlerController implements ExceptionMapper<Exception> {

    /*@Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;*/

    @Context
    private ResourceInfo resourceInfo;

    @Context private UriInfo uriInfo;

    @Override
    public Response toResponse(Exception ex) {
        log.error("Error processing request", ex);
        ex.printStackTrace();
        var error = new ErrorDto();
        if (ex instanceof MovieNotFoundException){
            error.setCode(422);
            log.warn("Error processing request: {}", kv("error", error));
            return Response.status(Response.Status.fromStatusCode(error.getCode())).build();
        }
        error.setCode(500);
        log.error("Error processing request: {}", kv("error", error));
        return Response.serverError().entity(error).build();
    }

}

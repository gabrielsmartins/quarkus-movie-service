package br.gasmartins.movies.application.web.mapper;

import br.gasmartins.movies.application.web.dto.ActorDto;
import br.gasmartins.movies.domain.Actor;
import br.gasmartins.movies.domain.enums.Gender;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActorControllerMapper {

    public Actor mapToDomain(ActorDto actorDto){
        var mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<ActorDto, Actor>() {
            @Override
            protected void configure() {
                using((Converter<Character, Gender>) mappingContext -> Gender.fromPrefix(mappingContext.getSource()))
                        .map(this.source.getGender(), this.destination.getGender());
            }
        });
        return mapper.map(actorDto, Actor.class);
    }

    public ActorDto mapToDto(Actor actor){
        var mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<Actor, ActorDto>() {
            @Override
            protected void configure() {
                using((Converter<Gender, String>) mappingContext -> mappingContext.getSource().getPrefix().toString()).map(this.source.getGender(), this.destination.getGender());
            }
        });
        return mapper.map(actor, ActorDto.class);
    }
}

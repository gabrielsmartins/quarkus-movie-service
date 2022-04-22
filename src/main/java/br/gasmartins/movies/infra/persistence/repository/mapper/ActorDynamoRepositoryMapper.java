package br.gasmartins.movies.infra.persistence.repository.mapper;

import br.gasmartins.movies.domain.Actor;
import br.gasmartins.movies.infra.persistence.entity.ActorEntity;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActorDynamoRepositoryMapper {

    public ActorEntity mapToEntity(Actor actor) {
        var mapper = new ModelMapper();
        return mapper.map(actor, ActorEntity.class );
    }

    public Actor mapToDomain(ActorEntity actorEntity) {
        var mapper = new ModelMapper();
        return mapper.map(actorEntity, Actor.class);
    }
}

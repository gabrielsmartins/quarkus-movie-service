package br.gasmartins.movies.infra.persistence.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.movies.domain.support.ActorSupport.defaultActor;
import static br.gasmartins.movies.infra.persistence.entity.support.ActorEntitySupport.defaultActorEntity;
import static org.assertj.core.api.Assertions.assertThat;

public class ActorDynamoRepositoryMapperTest {

    private ActorDynamoRepositoryMapper mapper;

    @BeforeEach
    public void setup(){
        this.mapper = new ActorDynamoRepositoryMapper();
    }

    @Test
    @DisplayName("Given Actor When Map Then Return Actor Entity")
    public void givenActorWhenMapThenReturnActorEntity(){
        var actorEntity = defaultActorEntity().build();
        var actor = this.mapper.mapToDomain(actorEntity);
        assertThat(actor).hasNoNullFieldsOrProperties();
        assertThat(actor.getName()).isEqualTo(actorEntity.getName());
        assertThat(actor.getGender()).isEqualTo(actorEntity.getGender());
        assertThat(actor.getRole()).isEqualTo(actorEntity.getRole());
        assertThat(actor.getDateOfBirthday()).isEqualTo(actorEntity.getDateOfBirthday());
    }

    @Test
    @DisplayName("Given Actor Entity When Map Then Return Actor")
    public void givenActorEntityWhenMapThenReturnActor(){
        var actor = defaultActor().build();
        var actorEntity = this.mapper.mapToEntity(actor);
        assertThat(actorEntity).hasNoNullFieldsOrProperties();
        assertThat(actorEntity.getName()).isEqualTo(actor.getName());
        assertThat(actorEntity.getGender()).isEqualTo(actor.getGender());
        assertThat(actorEntity.getRole()).isEqualTo(actor.getRole());
        assertThat(actorEntity.getDateOfBirthday()).isEqualTo(actor.getDateOfBirthday());
    }
}

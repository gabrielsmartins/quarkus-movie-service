package br.gasmartins.movies.application.web.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.gasmartins.movies.application.web.dto.ActorDtoSupport.defaultActorDto;
import static br.gasmartins.movies.domain.support.ActorSupport.defaultActor;
import static org.assertj.core.api.Assertions.assertThat;

public class ActorControllerMapperTest {

    private ActorControllerMapper mapper;

    @BeforeEach
    public void setup(){
        this.mapper = new ActorControllerMapper();
    }

    @Test
    @DisplayName("Given Actor When Map Then Return Actor Dto")
    public void givenActorWhenMapThenReturnActorDto(){
        var actorDto = defaultActorDto().build();
        var actor = this.mapper.mapToDomain(actorDto);
        assertThat(actor).hasNoNullFieldsOrProperties();
        assertThat(actor.getName()).isEqualTo(actorDto.getName());
        assertThat(actor.getGender().getPrefix().toString()).isEqualTo(actorDto.getGender());
        assertThat(actor.getRole()).isEqualTo(actorDto.getRole());
        assertThat(actor.getDateOfBirthday()).isEqualTo(actorDto.getDateOfBirthday());
    }

    @Test
    @DisplayName("Given Actor Dto When Map Then Return Actor")
    public void givenActorDtoWhenMapThenReturnActor(){
        var actor = defaultActor().build();
        var actorDto = this.mapper.mapToDto(actor);
        assertThat(actorDto).hasNoNullFieldsOrProperties();
        assertThat(actorDto.getName()).isEqualTo(actor.getName());
        assertThat(actorDto.getGender()).isEqualTo(actor.getGender().getPrefix().toString());
        assertThat(actorDto.getRole()).isEqualTo(actor.getRole());
        assertThat(actorDto.getDateOfBirthday()).isEqualTo(actor.getDateOfBirthday());
    }
}

package br.gasmartins.movies.application.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class MovieDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("duration")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime duration;

    @Singular
    @JsonProperty("actors")
    private List<ActorDto> actors;

}

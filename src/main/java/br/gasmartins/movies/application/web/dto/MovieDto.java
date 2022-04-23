package br.gasmartins.movies.application.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String name;

    @JsonProperty("genre")
    @NotNull
    private String genre;

    @JsonProperty("year")
    @NotNull
    private Integer year;

    @JsonProperty("directed_by")
    @NotNull
    private String directedBy;

    @JsonProperty("duration")
    @JsonFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime duration;

    @Singular
    @JsonProperty("actors")
    private List<@Valid ActorDto> actors;

}

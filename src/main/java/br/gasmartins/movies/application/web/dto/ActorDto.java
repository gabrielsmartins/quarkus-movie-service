package br.gasmartins.movies.application.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class ActorDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private String role;

    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirthday;

    @JsonProperty("gender")
    private Character gender;

}

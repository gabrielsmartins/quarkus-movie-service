package br.gasmartins.movies.application.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
@ToString
public class ActorDto {

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("role")
    @NotNull
    private String role;

    @JsonProperty("dob")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate dateOfBirthday;

    @JsonProperty("gender")
    @Pattern(regexp = "M|F")
    private String gender;

}

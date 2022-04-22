package br.gasmartins.movies.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Genre {

    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    THRILLER("Thriller");

    private final String description;

    public static Genre fromDescription(String description){
        return Stream.of(Genre.values())
                     .filter(genre -> genre.getDescription().equalsIgnoreCase(description))
                     .findFirst().orElse(null);
    }
}

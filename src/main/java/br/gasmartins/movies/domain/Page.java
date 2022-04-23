package br.gasmartins.movies.domain;

import java.util.List;

public interface Page <T> {

    List<T> getContent();
    boolean isEmpty();

    Pageable getPageable();
    long getTotalPages();
    long getTotalElements();

}

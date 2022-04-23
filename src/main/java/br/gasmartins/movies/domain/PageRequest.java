package br.gasmartins.movies.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageRequest implements Pageable {

    private final int page;
    private final int size;

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public int getPageNumber() {
        return this.page;
    }

}

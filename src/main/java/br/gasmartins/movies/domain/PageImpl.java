package br.gasmartins.movies.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PageImpl<T> implements Page<T> {

    private final List<T> content;
    private final Pageable pageable;
    private final long total;


    @Override
    public List<T> getContent() {
        return this.content;
    }

    @Override
    public boolean isEmpty() {
        return this.content.isEmpty();
    }

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }


    @Override
    public long getTotalPages() {
        return pageable.getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) pageable.getPageSize());
    }

    @Override
    public long getTotalElements() {
        return this.total;
    }

}

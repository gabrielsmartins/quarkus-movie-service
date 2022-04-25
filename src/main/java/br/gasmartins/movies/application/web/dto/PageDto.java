package br.gasmartins.movies.application.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDto<T> {

    @JsonProperty("data")
    private List<T> content;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("total_pages")
    private long totalPages;

    @JsonProperty("total_elements")
    private long totalElements;

}

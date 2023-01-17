package com.pequla.model;

import lombok.Data;

import java.util.List;

@Data
public class PagedData {
    private List<CachedData> content;
    private PageableModel pageable;
    private Boolean last;
    private Integer totalPages;
    private Integer totalElements;
    private Integer size;
    private Integer number;
    private SortModel sort;
    private Boolean first;
    private Integer numberOfElements;
    private Boolean empty;
}

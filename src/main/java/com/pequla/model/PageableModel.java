package com.pequla.model;

import lombok.Data;

@Data
public class PageableModel {
    private SortModel sort;
    private Integer offset;
    private Integer pageNumber;
    private Integer pageSize;
    private Boolean paged;
    private Boolean unpaged;
}

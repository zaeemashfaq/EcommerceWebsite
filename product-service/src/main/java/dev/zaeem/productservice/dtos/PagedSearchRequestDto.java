package dev.zaeem.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedSearchRequestDto {
    int pageNumber;
    int pageSize;
    String searchText;
}

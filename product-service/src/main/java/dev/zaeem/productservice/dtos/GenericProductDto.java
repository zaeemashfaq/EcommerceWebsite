package dev.zaeem.productservice.dtos;

import dev.zaeem.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericProductDto {
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}

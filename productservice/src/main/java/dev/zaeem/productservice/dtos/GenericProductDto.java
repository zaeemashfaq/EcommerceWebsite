package dev.zaeem.productservice.dtos;

import dev.zaeem.productservice.models.Category;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GenericProductDto {
    private String id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
    private String currency;
}

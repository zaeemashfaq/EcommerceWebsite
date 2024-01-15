package dev.zaeem.productservice.dtos;

import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Product;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GenericProductDto implements Serializable {
    private String id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
    private String currency;
    public static GenericProductDto from(Product product){
        GenericProductDto genericProduct = new GenericProductDto();
        genericProduct.setTitle(product.getTitle());
        genericProduct.setDescription(product.getDescription());
        genericProduct.setCurrency(product.getCurrency());
        genericProduct.setImage(product.getImage());
        genericProduct.setCategory(product.getCategory().getName());
        genericProduct.setPrice(product.getPrice());
        return genericProduct;
    }
}

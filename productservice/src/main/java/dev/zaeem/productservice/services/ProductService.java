package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.models.Product;

//All methods in the controller needs to be in the service
public interface ProductService {
    GenericProductDto getProductById(Long id);
}

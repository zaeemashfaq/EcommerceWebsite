package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Product;

import java.util.List;
import java.util.UUID;

//All methods in the controller needs to be in the service
public interface ProductService {
    List<GenericProductDto> getAllProducts() throws NotFoundException;
    GenericProductDto getProductById(Long id) throws NotFoundException;
    GenericProductDto getProductById(UUID id) throws NotFoundException;
    GenericProductDto deleteProductById(Long id) throws NotFoundException;
    GenericProductDto deleteProductById(UUID id) throws NotFoundException;
    GenericProductDto createProduct(GenericProductDto product);
    GenericProductDto updateProductById(Long id, GenericProductDto product) throws NotFoundException;
    GenericProductDto updateProductById(UUID id, GenericProductDto product) throws NotFoundException;
}

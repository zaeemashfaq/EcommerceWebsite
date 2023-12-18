package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.exceptions.UnauthorizedUserException;
import dev.zaeem.productservice.security.models.JwtObject;

import java.util.List;
import java.util.UUID;

//All methods in the controller needs to be in the service
public interface ProductService {
    List<GenericProductDto> getAllProducts() throws NotFoundException;
    GenericProductDto getProductById(Long id) throws NotFoundException;
    GenericProductDto getProductById(JwtObject authTokenObj, UUID id) throws NotFoundException;
    GenericProductDto deleteProductById(Long id) throws NotFoundException;
    GenericProductDto deleteProductById(UUID id,long userId) throws NotFoundException, UnauthorizedUserException;
    GenericProductDto createProduct(GenericProductDto product);
    GenericProductDto updateProductById(Long id, GenericProductDto product) throws NotFoundException;
    GenericProductDto updateProductById(UUID id, GenericProductDto product, long userId) throws NotFoundException, UnauthorizedUserException;

//    Object deleteProductById(UUID uuid, long userId);
}

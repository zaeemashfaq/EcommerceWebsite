package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericCategoryDto;
import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Product;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<GenericCategoryDto> getAllCategories() throws NotFoundException;
    List<GenericProductDto> getProductByCategory(UUID uuid) throws NotFoundException;
}

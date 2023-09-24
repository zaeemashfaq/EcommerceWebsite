package dev.zaeem.productservice.services;

import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Product;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories() throws NotFoundException;
    List<Product> getProductByCategory(Category category) throws NotFoundException;
}

package dev.zaeem.productservice.services;

import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Product;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    @Override
    public List<Category> getAllCategories() throws NotFoundException {
        return null;
    }

    @Override
    public List<Product> getProductByCategory(Category category) throws NotFoundException {
        return null;
    }
}

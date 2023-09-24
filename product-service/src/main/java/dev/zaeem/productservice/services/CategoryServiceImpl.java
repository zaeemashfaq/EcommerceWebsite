package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericCategoryDto;
import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Category;
import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.repositories.CategoryRepository;
import dev.zaeem.productservice.repositories.ProductRepository;
import dev.zaeem.productservice.convertor.Convertor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    Convertor convertor;
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository, Convertor convertor){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.convertor = convertor;
    }
    @Override
    public List<GenericCategoryDto> getAllCategories() throws NotFoundException {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new NotFoundException("No categories exist!");
        }
        List<GenericCategoryDto> genericCategories = new ArrayList<>();
        for(Category category: categories){
            genericCategories.add(convertor.convertCategoryToGenericCategory(category));
        }
        return genericCategories;
    }

    @Override
    public List<GenericProductDto> getProductByCategory(UUID uuid) throws NotFoundException {
        Optional<Category> fetchedCategory = categoryRepository.findById(uuid);
        if(fetchedCategory.isEmpty()){
            throw new NotFoundException("The category searched for does not exist!");
        }
        Category category = fetchedCategory.get();
        List<Product> products = productRepository.findAllByCategory_Name(category.getName());
        if(products.isEmpty()){
            throw new NotFoundException("There are no products under searched category");
        }
        List<GenericProductDto> genericProducts = new ArrayList<>();
        for(Product product: products){
            genericProducts.add(convertor.convertProductToGenericProductDto(product));
        }
        return genericProducts;
    }
}

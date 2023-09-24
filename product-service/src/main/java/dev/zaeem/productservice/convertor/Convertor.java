package dev.zaeem.productservice.convertor;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.models.*;
//import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class Convertor {
    CategoryRepository categoryRepository;
    public Convertor(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public Product convertGenericProductToProduct(GenericProductDto genericProduct){
        Product product = new Product();
        product.setTitle(genericProduct.getTitle());
        product.setDescription(genericProduct.getDescription());
        product.setImage(genericProduct.getImage());
        product.setPrice(genericProduct.getPrice());
        product.setCurrency(genericProduct.getCurrency());
        Optional<Category> fetchedCategory = categoryRepository.findByName(genericProduct.getCategory());
        Category category=null;
        if(fetchedCategory.isEmpty()){
            category = new Category();
            category.setName(genericProduct.getCategory());
            List<Product> products = new ArrayList<>();
            products.add(product);
            category.setProducts(products);
        }
        else {
            category = fetchedCategory.get();
            List<Product> products = category.getProducts();
            products.add(product);
            category.setProducts(products);
        }
        product.setCategory(category);
        return  product;
    }
    public GenericProductDto convertProductToGenericProductDto(Product product){
        GenericProductDto genericProduct = new GenericProductDto();
        genericProduct.setTitle(product.getTitle());
        genericProduct.setDescription(product.getDescription());
        genericProduct.setId(product.getUuid().toString());
        genericProduct.setPrice(product.getPrice());
        genericProduct.setImage(product.getImage());
        genericProduct.setCategory(product.getCategory().getName());
        genericProduct.setCurrency(product.getCurrency());
        return genericProduct;
    }
}

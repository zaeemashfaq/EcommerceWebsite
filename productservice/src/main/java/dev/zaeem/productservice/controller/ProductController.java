package dev.zaeem.productservice.controller;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
//    @Autowired
    //field injection. Not recommended.

    //Dependency Injection. Here, ProductService which is a dependency is being injected
    private ProductService productService;
    //constructor injection. This is recommended practice.
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService){ //if there are more than one implementation for the ProductService interface,
        //Spring wouldn't know which one to use. @Qualifier tells Spring which one to use!
        this.productService = productService;
    }
    // setter injection. not recommended
//    @Autowired
//    public void setProductService(ProductService productService){
//        this.productService = productService;
//    }
    @GetMapping
    public void getAllProducts() {
    }
    // localhost:8080/products/123
    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }
    @DeleteMapping("{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") Long id){
        return productService.deleteProductById(id);
    }
    //@RequestBody converts whatever is in the request body to GenericProductDto
    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product){
        return productService.createProduct(product);
    }
    @PutMapping("{id}")
    public GenericProductDto updateProductById(@PathVariable("id") Long id,GenericProductDto product){
        return productService.updateProductById(id,product);
    }
}

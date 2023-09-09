package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.models.Product;
import org.springframework.stereotype.Service;

@Service("selfProductServiceImpl")
public class SelfProductServiceImpl implements ProductService {
    @Override
    public GenericProductDto getProductById(Long id){
        return null;
    }
    public GenericProductDto createProduct(GenericProductDto product){
        return null;
    }
}

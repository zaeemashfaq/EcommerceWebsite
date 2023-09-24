package dev.zaeem.productservice.thirdpartyclients.productservice;

import dev.zaeem.productservice.dtos.FakeStoreProductDto;
import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.services.FakeStoreProductService;

import java.util.List;

public interface ThirdpartyProductService {
    List<FakeStoreProductDto> getAllProducts();
    FakeStoreProductDto getProductById(Long id) throws NotFoundException;
    FakeStoreProductDto deleteProductById(Long id);
    FakeStoreProductDto createProduct(GenericProductDto product);
    FakeStoreProductDto updateProductById(Long id, GenericProductDto product);
}

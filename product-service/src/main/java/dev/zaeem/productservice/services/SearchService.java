package dev.zaeem.productservice.services;

import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {
    private ProductRepository productRepository;
    public SearchService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public Optional<List<Product>> searchProductBySearchText(String searchText){
        return productRepository.findAllByTitleContainingIgnoreCase(searchText);
    }
    public List<Product> searchProductBySearchTextPaged(String searchText,int pageNumber, int pageSize){
        Page<Product> productsPage = productRepository.findAllByTitleContainingOrderByTitle(
                searchText, PageRequest.of(pageNumber,pageSize));
        List<Product> products = productsPage.toList();
        return products;
    }
}

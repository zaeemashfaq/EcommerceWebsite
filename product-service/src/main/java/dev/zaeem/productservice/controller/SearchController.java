package dev.zaeem.productservice.controller;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.dtos.PagedSearchRequestDto;
import dev.zaeem.productservice.dtos.SearchRequestDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.models.Product;
import dev.zaeem.productservice.services.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }
    @PostMapping
    public ResponseEntity<List<GenericProductDto>> searchProduct(@RequestBody SearchRequestDto requestDto) throws
            NotFoundException{
        Optional<List<Product>> productsOptional = searchService.searchProductBySearchText(requestDto.getSearchText());
        if(productsOptional.isEmpty()){
            throw new NotFoundException("The product searched for does not exist!");
        }
        List<Product> products = productsOptional.get();
        List<GenericProductDto> genericProducts = new ArrayList<>();
        for (Product product: products){
            genericProducts.add(GenericProductDto.from(product));
        }
        return new ResponseEntity<List<GenericProductDto>>(genericProducts, HttpStatus.OK);
    }
    @PostMapping("/paged")
    public ResponseEntity<List<GenericProductDto>> searchProductPaged(
            @RequestBody PagedSearchRequestDto requestDto) throws NotFoundException{
        List<Product> products = searchService.searchProductBySearchTextPaged(
                requestDto.getSearchText(), requestDto.getPageNumber(), requestDto.getPageSize());
//        if(productsOptional.isEmpty()){
//            throw new NotFoundException("The product searched for does not exist!");
//        }
//        List<Product> products = productsOptional.get();
        List<GenericProductDto> genericProducts = new ArrayList<>();
        for (Product product: products){
            genericProducts.add(GenericProductDto.from(product));
        }
        return new ResponseEntity<List<GenericProductDto>>(genericProducts, HttpStatus.OK);
    }
}
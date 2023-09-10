package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.FakeStoreProductDto;
import dev.zaeem.productservice.dtos.GenericProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplateBuilder restTemplateBuilder;
    private String getProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String deleteProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String createProductRequestUrl = "https://fakestoreapi.com/products";
    private String updateProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }
    @Override
    public GenericProductDto getProductById(Long id){
        //return new Product();
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response =
                restTemplate.getForEntity(getProductRequestUrl, FakeStoreProductDto.class,id);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        GenericProductDto product = new GenericProductDto();    //Product();
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.getCategory();
//        response.getStatusCode();
    return product;
    }
    @Override
    public GenericProductDto deleteProductById(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response =
                restTemplate.exchange(deleteProductRequestUrl, HttpMethod.DELETE,null,GenericProductDto.class,id);
        return response.getBody();
    }
    @Override
    public GenericProductDto createProduct(GenericProductDto product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response =
                restTemplate.postForEntity(createProductRequestUrl, product, GenericProductDto.class);
        return response.getBody();
    }
    public GenericProductDto updateProductById(Long id, GenericProductDto product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);*/
        HttpEntity<GenericProductDto> requestEntity = new HttpEntity<>(product);
        ResponseEntity<GenericProductDto> response =
                restTemplate.exchange(updateProductRequestUrl,HttpMethod.PUT,requestEntity,GenericProductDto.class,id);
        return response.getBody();
    }
}
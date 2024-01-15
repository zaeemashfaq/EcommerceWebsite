package dev.zaeem.productservice.services;

import dev.zaeem.productservice.dtos.FakeStoreProductDto;
import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.exceptions.UnauthorizedUserException;
import dev.zaeem.productservice.security.models.JwtObject;
import dev.zaeem.productservice.thirdpartyclients.productservice.fakestore.FakeStoreProductServiceClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private FakeStoreProductServiceClient fakeStoreProductServiceClient;
    private RedisTemplate<String ,Object> redisTemplate;
//    String -> datatype of KEY
//    KEY can be something like 1_18Dec_10AM where 1 is the product id
//    Object -> datatype of VALUE
    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient,
                                   RedisTemplate<String ,Object> redisTemplate){
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
        this.redisTemplate = redisTemplate;
    }
    public List<GenericProductDto>  getAllProducts() throws NotFoundException{
        List<GenericProductDto> list = new ArrayList<>();
        List<FakeStoreProductDto> fakeList = fakeStoreProductServiceClient.getAllProducts();
        for(FakeStoreProductDto fakeStoreProductDto: fakeList){
            list.add(convertFakeStoreToGenericProductDto(fakeStoreProductDto));
        }
        return list;
    }
    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException{
        //return new Product();
        GenericProductDto genericProductDto = (GenericProductDto) redisTemplate.opsForHash().get("PRODUCTS",id);
        if(genericProductDto!=null){
            return genericProductDto;
        }
        else {
            genericProductDto = convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.getProductById(id));
            redisTemplate.opsForHash().put("PRODUCTS",id,genericProductDto);
            return genericProductDto;
        }
    }
    @Override
    public GenericProductDto getProductById(JwtObject authTokenObj, UUID uuid) throws NotFoundException{
        return null;
    }
    @Override
    public GenericProductDto deleteProductById(Long id) throws NotFoundException{
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.deleteProductById(id));
    }
    @Override
    public GenericProductDto deleteProductById(UUID id,long userId) throws NotFoundException, UnauthorizedUserException {
        return null;
    }
    @Override
    public GenericProductDto createProduct(GenericProductDto product){
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.createProduct(product));
    }
    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto product) throws NotFoundException {
        return convertFakeStoreToGenericProductDto(fakeStoreProductServiceClient.updateProductById(id,product));
    }
    @Override
    public GenericProductDto updateProductById(UUID id, GenericProductDto product,long userId) throws NotFoundException, UnauthorizedUserException{
        return null;
    }
    private GenericProductDto convertFakeStoreToGenericProductDto(FakeStoreProductDto fakeStoreProductDto){
        GenericProductDto product = new GenericProductDto();
//        product.setId(fakeStoreProductDto.getId());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setCategory(fakeStoreProductDto.getCategory());
        return product;
    }
}
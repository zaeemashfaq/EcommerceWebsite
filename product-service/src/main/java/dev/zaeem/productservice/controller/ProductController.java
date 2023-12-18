package dev.zaeem.productservice.controller;

import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.exceptions.InactiveSessionException;
import dev.zaeem.productservice.exceptions.TokenNotFoundException;
import dev.zaeem.productservice.exceptions.UnauthorizedUserException;
import dev.zaeem.productservice.security.models.JwtObject;
import dev.zaeem.productservice.security.TokenValidator;
import dev.zaeem.productservice.security.models.Role;
import dev.zaeem.productservice.security.models.SessionStatus;
import dev.zaeem.productservice.services.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
//    @Autowired
    //field injection. Not recommended.

    //Dependency Injection. Here, ProductService which is a dependency is being injected
    private ProductService productService;
    private TokenValidator tokenValidator;
    //constructor injection. This is recommended practice.
    public ProductController(@Qualifier("selfProductServiceImpl") ProductService productService, TokenValidator tokenValidator){ //if there are more than one implementation for the ProductService interface,
        //Spring wouldn't know which one to use. @Qualifier tells Spring which one to use!
        this.productService = productService;
        this.tokenValidator = tokenValidator;
    }
    // setter injection. not recommended
//    @Autowired
//    public void setProductService(ProductService productService){
//        this.productService = productService;
//    }
    @GetMapping
    public List<GenericProductDto> getAllProducts() throws NotFoundException {
        return productService.getAllProducts();
    }
    // localhost:8080/products/123
    @GetMapping("/{id}")
    public GenericProductDto getProductById(@Nullable
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @PathVariable("id") String id) throws NotFoundException, InactiveSessionException {
        System.out.println(authToken);
        /*Optional<JwtObject> authTokenObjOptional;
        JwtObject authTokenObj;
        if(authToken!=null){
            authTokenObOptional = tokenValidator.validateToken(authToken);
            if(authTokenObjOptional.isEmpty()){
                //ignore
            }
            authTokenObj = authTokenObjOptional.get();
        }*/
        //Naman's code
        JwtObject authTokenObj = tokenValidator.validateToken(authToken);
        boolean isSessionActive = JwtObject.checkIfSessionActive(authTokenObj);
        if (!isSessionActive){
            /*if(authTokenObjOptional.isEmpty()){
                System.out.println("Invalid token!");
                //ignore
                */
            throw new InactiveSessionException("Session ended. Please login.");
        }
        /*My code below
        boolean isAuthorizred = tokenValidator.validateToken(authToken,userId);
        if(!isAuthorizred){
            throw new NotFoundException("User is not authorized to view this product");
        }*/
        return productService.getProductById(authTokenObj,UUID.fromString(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(
            @Nullable @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @PathVariable("id") String id)
            throws NotFoundException, TokenNotFoundException, InactiveSessionException, UnauthorizedUserException {

        if(authToken==null){
            throw new TokenNotFoundException("Please login before you can delete a product.");
        }
        JwtObject authTokenObj = tokenValidator.validateToken(authToken);
        boolean isSessionActive = JwtObject.checkIfSessionActive(authTokenObj);
        if (!isSessionActive){
            throw new InactiveSessionException("Your session has timed out. Please login again.");
        }
        ResponseEntity<GenericProductDto> responseEntity =
                new ResponseEntity<>(productService.deleteProductById(UUID.fromString(id),authTokenObj.getUserId()), HttpStatus.OK);
        return responseEntity;
    }
    //@RequestBody converts whatever is in the request body to GenericProductDto
    @PostMapping
    public GenericProductDto createProduct(@Nullable @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @RequestBody GenericProductDto genericProductDto)
            throws TokenNotFoundException,
            InactiveSessionException,
            UnauthorizedUserException {
        if(authToken==null){
            throw new TokenNotFoundException("Please login before you can delete a product.");
        }
        JwtObject authTokenObj = tokenValidator.validateToken(authToken);
        boolean isSessionActive = JwtObject.checkIfSessionActive(authTokenObj);
        if (!isSessionActive){
            throw new InactiveSessionException("Your session has timed out. Please login again.");
        }
        boolean isSeller = Role.checkIfSeller(authTokenObj);
        if(isSeller){
            return productService.createProduct(genericProductDto);
        }
        else {
            throw new UnauthorizedUserException("Please register as a seller first before you can list products.");
        }
    }
    @PutMapping("/{id}")
    public GenericProductDto updateProductById(@Nullable @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken,
            @PathVariable("id") String id,
            @RequestBody GenericProductDto genericProduct)
            throws NotFoundException, TokenNotFoundException, InactiveSessionException, UnauthorizedUserException {
        if(authToken==null){
            throw new TokenNotFoundException("Please login before you can delete a product.");
        }
        JwtObject authTokenObj = tokenValidator.validateToken(authToken);
        boolean isSessionActive = JwtObject.checkIfSessionActive(authTokenObj);
        if (!isSessionActive){
            throw new InactiveSessionException("Your session has timed out. Please login again.");
        }
        boolean isSeller = Role.checkIfSeller(authTokenObj);
        if(isSeller) {
            return productService.updateProductById(UUID.fromString(id), genericProduct,authTokenObj.getUserId());
        }
        else {
            throw new UnauthorizedUserException("Only sellers can update products.");
        }
    }
}

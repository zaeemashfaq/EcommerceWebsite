package dev.zaeem.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zaeem.productservice.dtos.GenericProductDto;
import dev.zaeem.productservice.exceptions.NotFoundException;
import dev.zaeem.productservice.security.models.JwtObject;
import dev.zaeem.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean(name="selfProductServiceImpl")
    private ProductService productService;
//    @MockBean
//    private SelfProductServiceImpl selfProductService;
    @Autowired
    private ObjectMapper objectMapper;

    /*@Test
    void getAllProductsByIdReturnsEmptyListWhenNoProducts() throws Exception{
        when(productService.getAllProducts())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }
    @Test
    void getAllProductsReturnsAllProductsWhenProductsExist() throws Exception{
        ArrayList<GenericProductDto> products = new ArrayList<>();
        products.add(new GenericProductDto());
        products.add(new GenericProductDto());
        products.add(new GenericProductDto());
        when(productService.getAllProducts())
                .thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(products)));
    }
    @Test
    void getProductByIdReturnsCorrectProductWhenProductExists() throws Exception{
        GenericProductDto productDto = new GenericProductDto();
        when(productService.getProductById(JwtObject.from(), UUID.fromString("dbb513a0-ec90-4c49-a5b1-719e6fbc3885")))
                .thenReturn(productDto);
        mockMvc.perform(get("/products/dbb513a0-ec90-4c49-a5b1-719e6fbc3885"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    void getProductByIdThrowsExceptionWhenProductDoesNotExist() throws NotFoundException{
        when(productService.getProductById(JwtObject.from(),UUID.fromString("dbb513a0-ec90-4c49-a5b1-719e6fbc3885")))
                .thenReturn(null);
//        assertThrows (NotFoundException.class,() -> productController.getProductById(UUID.fromString("dbb513a0-ec90-4c49-a5b1-719e6fbc3885")));
        assertThrows (NotFoundException.class,mockMvc.perform(get("/products/dbb513a0-ec90-4c49-a5b1-719e6fbc3885")));

        }
    }*/
}

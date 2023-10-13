package dev.zaeem.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zaeem.productservice.controller.ProductController;
import dev.zaeem.productservice.services.ProductService;
import dev.zaeem.productservice.services.SelfProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
//    @MockBean
//    private SelfProductServiceImpl selfProductService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProductsByIdReturnsEmptyListWhenNoProducts() throws Exception{
        when(productService.getAllProducts())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(status().is(404))
                .andExpect(content().string("[]"));
    }

}

package com.example.productcatalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)

public class ProductDeleteTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeleteExistingProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteNotFoundProduct() throws Exception {
        mockMvc.perform(delete("/products/99"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

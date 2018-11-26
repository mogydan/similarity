package com.mogydan.similarity.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.mogydan.similarity.SimilarityApplication;
import com.mogydan.similarity.model.Product;
import com.mogydan.similarity.utils.Utils;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SimilarityApplication.class
)
@TestExecutionListeners(value = DbUnitTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@AutoConfigureMockMvc
@DatabaseTearDown("/cleanAllTables.xml")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    public void getProduct() {
        mvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    public void getProductNotFound() {
        mvc.perform(get("/products/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id = 11 was not found"));
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    public void getAllProducts() {
        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("[" +
                                        "{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0}," +
                                        "{\"id\":2,\"name\":\"Mouse\",\"color\":\"Pink\",\"price\":21.0}" +
                                        "]")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/ProductControllerIntegrationTest/createProductExpected.xml", table = "PRODUCT")
    public void createProduct() {
        mvc.perform(
                post("/products")
                        .param("name", "Keyboard")
                        .param("color", "Violet")
                        .param("price", "123.99"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":5,\"name\":\"Keyboard\",\"color\":\"Violet\",\"price\":123.99}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/ProductControllerIntegrationTest/addAllProductsExpected.xml", table = "PRODUCT")
    public void addAllProducts() {
        mvc.perform(
                post("/products/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" +
                                "{\"name\":\"Ball\",\"color\":\"Black\",\"price\":2.0}," +
                                "{\"name\":\"Pen\",\"color\":\"Pink\",\"price\":21.0}" +
                                "]"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/ProductControllerIntegrationTest/deleteProductExpected.xml", table = "PRODUCT")
    public void deleteProduct() {
        mvc.perform(delete("/products/2"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/ProductControllerIntegrationTest/updateProductExpected.xml", table = "PRODUCT")
    public void updateProduct() {
        mvc.perform(
                put("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Keyboard\",\"color\": \"Violet\", \"price\": 123.99}")
        )
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/ProductControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/ProductControllerIntegrationTest/initialDatabase.xml", table = "PRODUCT")
    public void deleteProductNotFound() {
        mvc.perform(delete("/products/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id = 11 was not found"));
    }
}
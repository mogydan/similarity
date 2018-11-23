package com.mogydan.similarity.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.mogydan.similarity.SimilarityApplication;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
                .andExpect(content().json("{\"id\":3,\"name\":\"Keyboard\",\"color\":\"Violet\",\"price\":123.99}"));
    }

}
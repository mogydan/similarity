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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SimilarityApplication.class
)
@TestExecutionListeners(
        value = DbUnitTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@AutoConfigureMockMvc
@DatabaseTearDown("/cleanAllTables.xml")
public class RecommendedProductsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/RecommendedProductsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/RecommendedProductsControllerIntegrationTest/initialDatabase.xml")
    public void getBoughtTogetherProducts() {
        mockMvc.perform(get("/getRecommendedProducts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[" +
                                "{\"id\":3,\"name\":\"USB Hub\",\"color\":\"Pink\",\"price\":21.0}," +
                                "{\"id\":4,\"name\":\"Ball\",\"color\":\"White\",\"price\":55.0}" +
                                "]"));

        mockMvc.perform(get("/getRecommendedProducts/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[" +
                                "{\"id\":4,\"name\":\"Ball\",\"color\":\"White\",\"price\":55.0}" +
                                "]"));

        mockMvc.perform(get("/getRecommendedProducts/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[" +
                                "{\"id\":3,\"name\":\"USB Hub\",\"color\":\"Pink\",\"price\":21.0}" +
                                "]"));

        mockMvc.perform(get("/getRecommendedProducts/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[]"));

        mockMvc.perform(get("/getRecommendedProducts/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[]"));

        mockMvc.perform(get("/getRecommendedProducts/8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[]"));
    }
}
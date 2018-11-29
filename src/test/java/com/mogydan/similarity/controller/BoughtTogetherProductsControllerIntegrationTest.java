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
public class BoughtTogetherProductsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/BoughtTogetherProductsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/BoughtTogetherProductsControllerIntegrationTest/initialDatabase.xml")
    public void getBoughtTogetherProducts() {
        mockMvc.perform(get("/boughtTogetherProducts/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[" +
                                "{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0}," +
                                "{\"id\":4,\"name\":\"Ball\",\"color\":\"White\",\"price\":55.0}" +
                                "]"));

        mockMvc.perform(get("/boughtTogetherProducts/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0}]"));

        mockMvc.perform(get("/boughtTogetherProducts/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[{\"id\":4,\"name\":\"Ball\",\"color\":\"White\",\"price\":55.0}]"));

        mockMvc.perform(get("/boughtTogetherProducts/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content()
                        .json("[" +
                                "{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0}," +
                                "{\"id\":3,\"name\":\"USB Hub\",\"color\":\"Pink\",\"price\":21.0}" +
                                "]"));
    }
}
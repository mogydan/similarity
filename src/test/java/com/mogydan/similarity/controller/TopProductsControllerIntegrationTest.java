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
public class TopProductsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/TopProductsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/TopProductsControllerIntegrationTest/initialDatabase.xml")
    public void getTopProducts() {
        mvc.perform(get("/topProducts"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[" +
                                "{\"productId\":1,\"amount\":13}," +
                                "{\"productId\":3,\"amount\":4}," +
                                "{\"productId\":2,\"amount\":2}," +
                                "{\"productId\":4,\"amount\":1}" +
                                "]")
                );
    }
}
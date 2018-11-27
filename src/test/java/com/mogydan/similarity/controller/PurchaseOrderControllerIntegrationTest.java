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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
public class PurchaseOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/PurchaseOrderControllerIntegrationTest/addOrderExpected.xml")
    public void addOrder() {
        mvc.perform(
                post("/orders").param("customerId", "2"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":3,\"customer\":{\"id\":2,\"name\":null,\"email\":null}}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/PurchaseOrderControllerIntegrationTest/addOrdersExpected.xml")
    public void addOrders() {
        mvc.perform(
                post("/orders/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" +
                                "{\"id\":null,\"customer\":{\"id\":2,\"name\":null,\"email\":null}}," +
                                "{\"id\":null,\"customer\":{\"id\":1,\"name\":null,\"email\":null}}" +
                                "]"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    public void getOrder() {
        mvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    public void getOrderNotFound() {
        mvc.perform(get("/orders/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order with id = 11 was not found"));
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    public void getAllOrders() {
        mvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("[" +
                                        "{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}}," +
                                        "{\"id\":2,\"customer\":{\"id\":2,\"name\":\"Customer2\",\"email\":\"customer2@m.com\"}}" +
                                        "]")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/updateProductExpected.xml")
    public void updateOrder() {
        mvc.perform(
                put("/orders/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":2,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}}")
        )
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/deleteOrderExpected.xml")
    public void deleteOrder() {
        mvc.perform(delete("/orders/2"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/PurchaseOrderControllerIntegrationTest/initialDatabase.xml")
    public void deleteOrderNotFond() {
        mvc.perform(delete("/orders/22"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order with id = 22 was not found"));
    }
}
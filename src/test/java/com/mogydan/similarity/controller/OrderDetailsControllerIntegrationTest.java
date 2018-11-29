package com.mogydan.similarity.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
public class OrderDetailsControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/OrderDetailsControllerIntegrationTest/createOrderDetailExpected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void createOrderDetails() {
        mvc.perform(
                post("/orderDetails")
                        .param("amount", "1")
                        .param("productId", "4")
                        .param("orderId", "4"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/OrderDetailsControllerIntegrationTest/addListOfOrderDetailsExpected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addListOfOrderDetails() {
        mvc.perform(
                post("/orderDetails/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" +
                                "{\"id\":null,\"purchaseOrder\":{\"id\":4,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0},\"amount\":2}," +
                                "{\"id\":null,\"purchaseOrder\":{\"id\":4,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0},\"amount\":1}" +
                                "]"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    public void getOrderDetailsByOrderId() {
        mvc.perform(get("/orderDetails/byOrderId/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("[" +
                                "{\"id\":1,\"purchaseOrder\":{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0},\"amount\":2}," +
                                "{\"id\":2,\"purchaseOrder\":{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0},\"amount\":1}" +
                                "]"
                        )
                );
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    public void getAllOrderDetails() {
        mvc.perform(get("/orderDetails"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("[" +
                                        "{\"id\":1,\"purchaseOrder\":{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0},\"amount\":2}," +
                                        "{\"id\":2,\"purchaseOrder\":{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0},\"amount\":1}," +
                                        "{\"id\":3,\"purchaseOrder\":{\"id\":2,\"customer\":{\"id\":2,\"name\":\"Customer2\",\"email\":\"customer2@m.com\"}},\"product\":{\"id\":3,\"name\":\"USB Hub\",\"color\":\"Pink\",\"price\":21.0},\"amount\":1}," +
                                        "{\"id\":4,\"purchaseOrder\":{\"id\":3,\"customer\":{\"id\":3,\"name\":\"Customer3\",\"email\":\"customer3@m.com\"}},\"product\":{\"id\":3,\"name\":\"USB Hub\",\"color\":\"Pink\",\"price\":21.0},\"amount\":1}," +
                                        "{\"id\":5,\"purchaseOrder\":{\"id\":3,\"customer\":{\"id\":3,\"name\":\"Customer3\",\"email\":\"customer3@m.com\"}},\"product\":{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0},\"amount\":1}" +
                                        "]"
                                )
                );
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    public void getOrderDetailById() {
        mvc.perform(get("/orderDetails/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        "{\"id\":1,\"purchaseOrder\":{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}},\"product\":{\"id\":1,\"name\":\"Mouse\",\"color\":\"Black\",\"price\":2.0},\"amount\":2}"
                                )
                );
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    public void getOrderDetailByIdNotFound() {
        mvc.perform(get("/orderDetails/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("OrderDetail with id = 11 was not found"));
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/updateOrderDetailsExpected.xml")
    public void updateOrderDetails() {
        mvc.perform(
                put("/orderDetails/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"purchaseOrder\":{\"id\":2,\"customer\":{\"id\":2,\"name\":\"Customer2\",\"email\":\"customer2@m.com\"}},\"product\":{\"id\":2,\"name\":\"Keyboard\",\"color\":\"red\",\"price\":12.0},\"amount\":2}")
        )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/deleteOrderDetailsExpected.xml")
    public void deleteOrderDetail() {
        mvc.perform(delete("/orderDetails/5"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DatabaseSetup("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase("/OrderDetailsControllerIntegrationTest/initialDatabase.xml")
    public void deleteOrderDetailNotFound() {
        mvc.perform(delete("/orderDetails/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("OrderDetail with id = 11 was not found"));
    }
}
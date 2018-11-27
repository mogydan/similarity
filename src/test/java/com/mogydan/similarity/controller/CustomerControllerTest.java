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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/createCustomerExpected.xml", table = "CUSTOMER")
    public void createCustomer() {
        mvc.perform(
                post("/customers")
                        .param("name", "NewCustomer")
                        .param("email", "newEmail@m.com")
        )
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":5,\"name\":\"NewCustomer\",\"email\":\"newEmail@m.com\"}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/addAllCustomersExpected.xml", table = "CUSTOMER")
    public void addCustomers() {
        mvc.perform(
                post("/customers/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[" +
                                "{\"name\":\"Customer3\",\"email\":\"customer3@m.com\"}," +
                                "{\"name\":\"Customer4\",\"email\":\"customer4@m.com\"}" +
                                "]"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    public void getCustomer() {
        mvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    public void getCustomerNotFound() {
        mvc.perform(get("/customers/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer with id = 11 was not found"));
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/initialDatabase.xml", table = "CUSTOMER")
    public void getAllCustomers() {
        mvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json("[" +
                                        "{\"id\":1,\"name\":\"Customer1\",\"email\":\"customer1@m.com\"}," +
                                        "{\"id\":2,\"name\":\"Customer2\",\"email\":\"customer2@m.com\"}" +
                                        "]")
                );
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/updateCustomerExpected.xml", table = "CUSTOMER")
    public void updateCustomer() {
        mvc.perform(
                put("/customers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"UpdatedCustomer2\",\"email\": \"updatedEmail2@m.com\"}")
        )
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/deleteCustomerExpected.xml", table = "CUSTOMER")
    public void deleteCustomer() {
        mvc.perform(delete("/customers/2"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    @DatabaseSetup("/CustomerControllerIntegrationTest/initialDatabase.xml")
    @ExpectedDatabase(value = "/CustomerControllerIntegrationTest/initialDatabase.xml", table = "CUSTOMER")
    public void deleteCustomerNotFound() {
        mvc.perform(delete("/customers/11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer with id = 11 was not found"));
    }
}
package com.javarefresh.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.utils.MockGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static PostgreSQLContainer mockDb = new PostgreSQLContainer("postgres")
            .withDatabaseName("java-refresh-integ")
            .withUsername("test")
            .withPassword("testpass");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", mockDb::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mockDb::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mockDb::getPassword);
    }

    @Test
    @Order(1)
    void containerIsRunning() {
        assertThat(mockDb.isRunning());
    }


    @Test
    @Order(2)
    void createOrder() throws Exception {
        OrderRequest order = MockGenerator.generateOrder();
        String json = objectMapper.writeValueAsString(order);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    void getAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void getOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/1"))
                .andExpect(status().isOk());
    }


}
package com.javarefresh.orderservice.service;

import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.utils.MockGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
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
    void createOrder() {
        OrderRequest orderRequest = MockGenerator.generateOrder();
        OrderRequest orderResponse = orderService.createOrder(orderRequest);
        Assertions.assertEquals(orderRequest.getOrderNumber(), orderResponse.getOrderNumber());
        Assertions.assertEquals(orderRequest.getOrderLineItemDtoList().size(), orderResponse.getOrderLineItemDtoList().size());
    }

    @Test
    @Order(3)
    void getOrderById() {
        OrderRequest order = orderService.getOrderById(1L);
        Assertions.assertEquals(MockGenerator.generateOrder().getOrderNumber(), order.getOrderNumber());
    }

    @Test
    @Order(2)
    void getAllOrders() {
        List<OrderRequest> orders = orderService.getAllOrders();
        Assertions.assertEquals(orders.size(), 1);
    }
}
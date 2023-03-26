package com.javarefresh.inventoryservice.service;

import com.javarefresh.inventoryservice.dto.InventoryRequest;
import com.javarefresh.inventoryservice.dto.InventoryResponse;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceTest {

    @Autowired
    private InventoryService sut;

    @Container
    private static final PostgreSQLContainer mockDb = new PostgreSQLContainer("postgres")
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
    @Order(2)
    void isInStock() {
        boolean res = sut.isInStock("abc123");
        Assertions.assertTrue(res);
    }

    @Test
    @Order(3)
    void getAll() {
        List<InventoryResponse> responses = sut.getAll();
        Assertions.assertEquals(1, responses.size());
    }

    @Test
    @Order(4)
    void areInStock() {
        List<InventoryResponse> responses = sut.areInStock(java.util.Arrays.stream(Arrays.array("abc123", "123abc")).collect(Collectors.toList()));
        Assertions.assertEquals(1, responses.size());
    }

    @Test
    @Order(1)
    void addToStock() {
        InventoryRequest request = InventoryRequest.builder()
                .skuCode("abc123")
                .quantity(5)
                .build();
        InventoryResponse response = sut.addToStock(request);
        Assertions.assertTrue(response.isInStock());

    }
}
package com.javarefresh.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class InventoryServiceTest {

    @Autowired
    private InventoryService sut;

    
    @Test
    void isInStock() {
        boolean res = sut.isInStock("abc");
        Assertions.assertFalse(res);

    }
}
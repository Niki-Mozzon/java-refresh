package com.javarefresh.inventoryservice.mapper;

import com.javarefresh.inventoryservice.dto.InventoryResponse;
import com.javarefresh.inventoryservice.model.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryMapperTest {

    @Autowired
    private InventoryMapper sut;
    @ParameterizedTest
    @ValueSource(ints={1,3})
    @DisplayName("GIVEN an inventory with quantity bigger than 0, WHEN parsed in InventoryResponse, THEN isInStock is true")
    void modelToDto(int quantity) {
         Inventory inventory = Inventory
                .builder()
                .id(1l)
                .skuCode("abc")
                .quantity(quantity)
                .build();
        InventoryResponse response = sut.modelToDto(inventory);
        Assertions.assertTrue(response.isInStock());
    }
}
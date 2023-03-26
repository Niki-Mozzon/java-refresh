package com.javarefresh.inventoryservice.service;

import com.javarefresh.inventoryservice.dto.InventoryRequest;
import com.javarefresh.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    boolean isInStock(String skuCode);

    List<InventoryResponse> getAll();

    List<InventoryResponse> areInStock(List<String> skuCode);


    InventoryResponse addToStock(InventoryRequest inventory);
}

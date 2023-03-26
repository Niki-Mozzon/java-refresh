package com.javarefresh.inventoryservice.service;

import com.javarefresh.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    boolean isInStock(String skuCode);
    List<InventoryResponse> areInStock(List<String> skuCode);


}

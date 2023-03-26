package com.javarefresh.inventoryservice.service;

import com.javarefresh.inventoryservice.dto.InventoryRequest;
import com.javarefresh.inventoryservice.dto.InventoryResponse;
import com.javarefresh.inventoryservice.mapper.InventoryMapper;
import com.javarefresh.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAll() {
        return inventoryRepository.findAll().stream().map(inventory -> inventoryMapper.modelToDto(inventory)).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> areInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                        inventoryMapper.modelToDto(inventory)
                )
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventoryResponse addToStock(InventoryRequest inventory) {
        return inventoryMapper.modelToDto(inventoryRepository.save(inventoryMapper.inventoryReqToModel(inventory)));
    }
}

package com.javarefresh.inventoryservice.controller;

import com.javarefresh.inventoryservice.dto.InventoryRequest;
import com.javarefresh.inventoryservice.dto.InventoryResponse;
import com.javarefresh.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> addToStock(@RequestBody InventoryRequest inventory) {
        return new ResponseEntity<>(inventoryService.addToStock(inventory), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryResponse>> getAll() {
        return new ResponseEntity<>(inventoryService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{skuCode}")
    public ResponseEntity<Boolean> isInStock(@PathVariable String skuCode) {
        return new ResponseEntity<>(inventoryService.isInStock(skuCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCodes) {
        return new ResponseEntity<>(inventoryService.areInStock(skuCodes), HttpStatus.OK);
    }


}

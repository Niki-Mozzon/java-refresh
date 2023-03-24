package com.javarefresh.orderservice.controller;

import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {

        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long id) {
        OrderRequest orderRequest = orderService.getOrderById(id);
        if (orderRequest == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(orderRequest, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequest>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

}

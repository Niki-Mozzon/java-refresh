package com.javarefresh.orderservice.service;

import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.mapper.OrderMapper;
import com.javarefresh.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderMapper orderMapper;
    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public OrderRequest createOrder(OrderRequest orderRequest) {
        return orderMapper.modelToDto(orderRepository.save(orderMapper.dtoToModel(orderRequest)));
    }

    @Override
    public OrderRequest getOrderById(Long id) {
        return orderMapper.modelToDto(orderRepository.findById(id).orElse(null));
    }

    @Override
    public List<OrderRequest> getAllOrders() {
        return orderRepository.findAll().stream().map(order -> orderMapper.modelToDto(order)).collect(Collectors.toList());
    }
}

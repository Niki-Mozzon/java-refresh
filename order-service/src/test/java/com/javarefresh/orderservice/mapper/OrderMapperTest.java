package com.javarefresh.orderservice.mapper;

import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.model.Order;
import com.javarefresh.orderservice.model.OrderLineItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {OrderMapperImpl.class})
class OrderMapperTest {

    @Autowired
    private OrderMapper sut;

    @Test
     void modelToDto(){
        Order order = Order
                .builder()
                .id(12L)
                .orderNumber("123")
                .orderLineItemList(
                        Arrays.asList(
                                OrderLineItem
                                        .builder()
                                        .id(1L)
                                        .price(BigDecimal.valueOf(2.99))
                                        .quantity(2)
                                        .skuCode("awd")
                                        .build(),
                                OrderLineItem
                                        .builder()
                                        .id(2L)
                                        .price(BigDecimal.valueOf(5.99))
                                        .quantity(3)
                                        .skuCode("dwa")
                                        .build()
                        )
                )
                .build();
        OrderRequest orderDto = sut.modelToDto(order);
        Assertions.assertEquals(order.getOrderNumber(),orderDto.getOrderNumber());
        Assertions.assertEquals(order.getId(),orderDto.getId());
        Assertions.assertEquals(order.getOrderLineItemList().size(),orderDto.getOrderLineItemDtoList().size());
    }

}
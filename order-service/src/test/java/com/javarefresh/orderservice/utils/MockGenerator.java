package com.javarefresh.orderservice.utils;

import com.javarefresh.orderservice.dto.OrderLineItemDto;
import com.javarefresh.orderservice.dto.OrderRequest;

import java.math.BigDecimal;
import java.util.Arrays;

public class MockGenerator {

    public static OrderRequest generateOrder() {
        return OrderRequest
                .builder()
                .orderNumber("888")
                .orderLineItemDtoList(
                        Arrays.asList(OrderLineItemDto
                                .builder()
                                .price(BigDecimal.valueOf(12.99))
                                .skuCode("ciccio")
                                .quantity(2)
                                .build()
                        )
                )
                .build();
    }
}

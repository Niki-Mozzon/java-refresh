package com.javarefresh.orderservice.dto;

import com.javarefresh.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderRequest {
    private Long id;
    private String orderNumber;
    private List<OrderLineItemDto> orderLineItemDtoList;
}

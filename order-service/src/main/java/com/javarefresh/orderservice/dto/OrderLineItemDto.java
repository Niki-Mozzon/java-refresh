package com.javarefresh.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItemDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}

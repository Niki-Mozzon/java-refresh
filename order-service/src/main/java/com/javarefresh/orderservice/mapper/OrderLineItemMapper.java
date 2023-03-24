package com.javarefresh.orderservice.mapper;

import com.javarefresh.orderservice.dto.OrderLineItemDto;
import com.javarefresh.orderservice.model.OrderLineItem;
import org.mapstruct.Mapper;

@Mapper
public interface OrderLineItemMapper {
    OrderLineItemDto modelToDto(OrderLineItem orderLineItem);
    OrderLineItem dtoToModel(OrderLineItemDto orderLineItemDto);
}

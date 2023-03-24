package com.javarefresh.orderservice.mapper;

import com.javarefresh.orderservice.dto.OrderRequest;
import com.javarefresh.orderservice.model.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "orderLineItemDtoList",target = "orderLineItemList")
    Order dtoToModel( OrderRequest orderRequest);
    @InheritInverseConfiguration
    OrderRequest modelToDto(Order order);
}

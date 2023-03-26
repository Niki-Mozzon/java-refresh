package com.javarefresh.inventoryservice.mapper;

import com.javarefresh.inventoryservice.dto.InventoryResponse;
import com.javarefresh.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(target = "isInStock",source = "quantity",qualifiedByName = "quantityToIsInStock")
    public InventoryResponse modelToDto(Inventory inventory);

    @Named("quantityToIsInStock")
    public static boolean quantityToBool(Integer quantity){
        return quantity>0;
    }
}

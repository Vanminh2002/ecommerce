package com.example.inventoryservice.mapper;

import com.example.inventoryservice.dto.request.CreateInventoryRequest;
import com.example.inventoryservice.dto.request.UpdateInventoryRequest;
import com.example.inventoryservice.dto.response.InventoryResponse;
import com.example.inventoryservice.entities.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toDto(CreateInventoryRequest createInventoryRequest);

    InventoryResponse toResponse(Inventory inventory);


    public  void toUpdateInventory(@MappingTarget Inventory inventory , UpdateInventoryRequest request);

}

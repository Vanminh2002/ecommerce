package com.example.inventoryservice.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateInventoryRequest {
    Integer quantity;
    Integer reserved;
    Integer sold;
}

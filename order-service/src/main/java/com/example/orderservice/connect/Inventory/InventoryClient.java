package com.example.orderservice.connect.Inventory;

import com.example.commonlib.dto.inventory.DecreaseStockRequest;
import com.example.orderservice.dto.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/inventory/decrease")
    ApiResponse<Void> decreaseStock(@RequestBody DecreaseStockRequest request);
}

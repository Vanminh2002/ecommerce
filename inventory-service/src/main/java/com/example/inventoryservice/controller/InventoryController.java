package com.example.inventoryservice.controller;

import com.example.commonlib.dto.api.ApiResponse;
import com.example.commonlib.dto.inventory.DecreaseStockRequest;
import com.example.inventoryservice.dto.request.CreateInventoryRequest;
import com.example.inventoryservice.dto.response.InventoryResponse;
import com.example.inventoryservice.service.InventoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryController {
    InventoryService inventoryService;

    @PostMapping("create")
    ApiResponse<InventoryResponse> createInventory(@RequestBody CreateInventoryRequest request) {
        InventoryResponse response = inventoryService.createInventory(request);

        return ApiResponse.<InventoryResponse>builder()
                .message("Tạo mới kho thành công")
                .data(response)
                .build();

    }

    @GetMapping("get-by/{id}")
    ApiResponse<InventoryResponse> getInventoryById(@PathVariable Long id) {
        InventoryResponse response = inventoryService.getInventoryById(id);
        return ApiResponse.<InventoryResponse>builder()
                .message("lấy kho có id = " + id)
                .data(response)
                .build();
    }

    @GetMapping("get-product/{id}")
    ApiResponse<InventoryResponse> getInventoryByProductId(@PathVariable Long id) {
        InventoryResponse response = inventoryService.findByProductId(id);

        return ApiResponse.<InventoryResponse>builder()
                .message("lấy thông tin sản phầm có id = " + id)
                .data(response)
                .build();
    }


    @DeleteMapping("delete/{id}")
    ApiResponse<Void> deleteInventoryById(@PathVariable Long id) {
        inventoryService.deleteInventoryById(id);
        return ApiResponse.<Void>builder()
                .message("Đã xóa kho có id = " + id)
                .build();
    }

    @PostMapping("/stock")
    public ApiResponse<Map<Long, Integer>> getStock(@RequestBody List<Long> productIds) {
        return ApiResponse.<Map<Long, Integer>>builder()
                .data(inventoryService.getStock(productIds))
                .build();
    }

    @PostMapping("decrease")
    public ApiResponse<?> decreaseStock(@RequestBody DecreaseStockRequest request) {
        try {
            inventoryService.decreaseStock(request);
            return ApiResponse.builder()
                    .message("Ok")
                    .build();
        } catch (Exception e) {
            log.info("Looix" ,e);
            return ApiResponse.builder()
                    .code(500)
                    .message("Error")
                    .build();
        }
    }
}

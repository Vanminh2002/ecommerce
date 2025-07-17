package com.example.inventoryservice.service;

import com.example.commonlib.dto.api.ApiResponse;
import com.example.commonlib.dto.inventory.DecreaseStockRequest;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.inventoryservice.dto.request.CreateInventoryRequest;
import com.example.inventoryservice.dto.response.InventoryResponse;
import com.example.inventoryservice.entities.Inventory;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryService {
    InventoryRepository inventoryRepository;
    InventoryMapper inventoryMapper;


    public InventoryResponse createInventory(CreateInventoryRequest request) {
        if (inventoryRepository.existsByWarehouse(request.getWarehouse())) {
            throw new AppException(ErrorCode.EXISTED);
        }

        Inventory inventory = inventoryMapper.toDto(request);
        inventory.setUpdatedAt(LocalDateTime.now());
        var save = inventoryRepository.save(inventory);
        return inventoryMapper.toResponse(save);
    }

    public InventoryResponse findByProductId(Long productId) {
        var product = inventoryRepository.findByProductId(productId);
        if (product == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return inventoryMapper.toResponse(product);
    }

    public InventoryResponse getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return inventoryMapper.toResponse(inventory);
    }


    public void deleteInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        inventoryRepository.deleteById(id);
    }


    public Map<Long, Integer> getStock(List<Long> productIds) {
        List<Inventory> inventories = inventoryRepository.findByProductIdIn(productIds);
        Map<Long, Integer> result = new HashMap<>();
        for (Inventory inv : inventories) {
            result.put(inv.getProductId(), inv.getQuantity());
        }
        // Nếu muốn trả về 0 cho sản phẩm không có trong kho:
        for (Long id : productIds) {
            result.putIfAbsent(id, 0);
        }
        return result;
    }


    public void decreaseStock(DecreaseStockRequest request) {
        try {

            // kiểm tra đủ hàng cho tất cả sản phầm
            for (DecreaseStockRequest.Item item : request.getItems()) {
                if (item.getProductId() == null || item.getQuantity() == null) {
                    throw new AppException(ErrorCode.NOT_FOUND);
                }
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId());
                if (inventory == null) {
                    throw new AppException(ErrorCode.NOT_FOUND);
                }
                if (inventory.getQuantity() < item.getQuantity()) {
                    throw new AppException(ErrorCode.INSUFFICIENT_QUANTITY);
                }
            }
            // nếu đủ thì trừ vào kho
            for (DecreaseStockRequest.Item item : request.getItems()) {
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId());
                inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
                inventory.setUpdatedAt(LocalDateTime.now());
                inventoryRepository.save(inventory);

            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new AppException(ErrorCode.ERROR_SERVER);
        }

    }


}

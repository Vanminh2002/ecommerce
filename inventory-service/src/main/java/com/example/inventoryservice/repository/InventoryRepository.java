package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProductId(Long productId);
    boolean existsByWarehouse(String warehouse);

    List<Inventory> findByProductIdIn(List<Long> productIds);

}

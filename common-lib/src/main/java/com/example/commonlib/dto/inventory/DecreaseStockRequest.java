package com.example.commonlib.dto.inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class DecreaseStockRequest {
    List<Item> items;

    public static class Item {
        Long productId;
        Integer quantity;

        public Item() {
        }

        public Item(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

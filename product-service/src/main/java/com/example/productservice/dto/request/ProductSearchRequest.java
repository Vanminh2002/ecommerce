package com.example.productservice.dto.request;

import com.example.commonlib.dto.BaseFilter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchRequest extends BaseFilter {
    Long id;
    String productName;
    Double price;
    Integer quantity;
    Integer inventory;
}

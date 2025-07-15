package com.example.categoryservice.dto.category.request;

import com.example.commonlib.dto.BaseFilter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class CategorySearchRequest extends BaseFilter {
    Long id;
    String categoryName;
    String description;
//    @Min(0)
//    Integer pageNo = 0;
//    //    @Size(min = 1 ,max = 50)// không đùng được với Integer
//    @Min(1)
//    @Max(100)
//    Integer pageSize = 10;
//
//    String sort = "createdAt";
//    String sortDirection = "desc";
}

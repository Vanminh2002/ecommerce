package com.example.commonlib.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseFilter {
    Integer page;
    Integer size;
    String sortBy = "id";
    String sortDir = "desc";
    String sortBy2;
    String sortDir2;
    String keyword;
    String value;


}

package com.example.userservice.dto.paginate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginateResult<T> {
    private List<T> data;
    private int page;
    private int limit;
    private long total;
}

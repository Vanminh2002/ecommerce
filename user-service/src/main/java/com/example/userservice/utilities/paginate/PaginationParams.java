package com.example.userservice.utilities.paginate;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data

public class PaginationParams {
    private int page = 1;
    private int limit = 10;
    private Map<String, String> filters = new HashMap<>();
}

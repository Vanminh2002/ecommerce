package com.example.userservice.utilities.paginate;

import lombok.Data;

@Data
public class PaginationMeta {
    private int page;
    private int limit;
    private long total;
    private int totalPages;

    public PaginationMeta(int page, int limit, long total, int totalPages) {
        this.page = page;
        this.limit = limit;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / limit);
    }
}

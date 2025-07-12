package com.example.userservice.utilities.paginate;

import com.example.userservice.dto.paginate.PaginateResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Map;

public class PaginateService<T> {
    public PaginateResult<T> paginate(
            JpaSpecificationExecutor<T> repo,
            PaginationParams params,
            Map<String, FilterType> filterConfig
    ) {
        Pageable pageable = PageRequest.of(Math.max(0, params.getPage() - 1), params.getLimit());
        Specification<T> spec = new FilterBuilder<T>().build(params.getFilters(), filterConfig);

        Page<T> page = repo.findAll(spec, pageable);
        return new PaginateResult<>(page.getContent(), page.getNumber() + 1, page.getSize(), page.getTotalElements());
    }
}

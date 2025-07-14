package com.example.categoryservice.repository;

import com.example.categoryservice.dto.category.request.CategorySearchRequest;
import com.example.categoryservice.entities.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryRepositoryCustom {
    // để inject 1 entityManager - 1 công cụ quan trọng để thao tác với DB khi làm việc với Jpa
    @PersistenceContext
    EntityManager entityManager;

    public Page<Category> categoryPage(CategorySearchRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // Query để lấy danh sách
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);

        List<Predicate> predicates = new ArrayList<>();
        if (request.getCategoryName() != null) {//&& !request.getCategoryName().isEmpty()
            predicates.add(cb.like(root.get("categoryName"), "%" + request.getCategoryName() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Category> query = entityManager.createQuery(cq);
        query.setFirstResult(request.getPageNo() * request.getPageSize());
        query.setMaxResults(request.getPageSize());
        List<Category> categories = query.getResultList();

        // đếm tổng số bản ghi
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Category> countRoot = countQuery.from(Category.class);
        List<Predicate> countPredicates = new ArrayList<>();
        if (request.getCategoryName() != null) {
            countPredicates.add(cb.like(cb.lower(countRoot.get("categoryName")), "%" + request.getCategoryName().toLowerCase() + "%"));
        }
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Order order = "desc".equalsIgnoreCase(request.getSortDirection())
                ? cb.desc(root.get(request.getSort()))
                : cb.asc(root.get(request.getSort()));
        cq.orderBy(order);
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(categories, PageRequest.of(request.getPageNo(), request.getPageSize()), total);

    }
}

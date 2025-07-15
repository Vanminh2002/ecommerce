package com.example.productservice.repository;

import com.example.productservice.dto.request.ProductSearchRequest;
import com.example.productservice.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PrePersist;
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
public class ProductRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public Page<Product> findProducts(ProductSearchRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = buildPredicates(request, cb, root);
        cq.where(predicates.toArray(new Predicate[0]));

        String sortBy = request.getSortBy() != null ? request.getSortBy() : "id";
        String sortDir = request.getSortDir() != null ? request.getSortDir() : "asc";

        Path<?> sortByPath = root.get(sortBy);
        Order order = "asc".equalsIgnoreCase(sortDir) ? cb.asc(sortByPath) : cb.desc(sortByPath);

        cq.orderBy(order);

        // phân trang

        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        TypedQuery<Product> query = entityManager.createQuery(cq);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Product> products = query.getResultList();


        // đếm tổng số bản ghi
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> productRoot = countQuery.from(Product.class);
        List<Predicate> countPredicates = buildPredicates(request, cb, productRoot);
        countQuery.select(cb.count(productRoot))
                .where(countPredicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(products, PageRequest.of(page, size), total);


    }

    private static List<Predicate> buildPredicates(ProductSearchRequest request, CriteriaBuilder cb, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getProductName() != null && !request.getProductName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("productName")),
                    "%" + request.getProductName().toLowerCase() + "%"));
        }
        if (request.getPrice() != null && request.getPrice() > 0) {
            predicates.add(cb.equal(root.get("price"), request.getPrice()));
        }
        if (request.getQuantity() != null && request.getQuantity() > 0) {
            predicates.add(cb.equal(root.get("quantity"), request.getQuantity()));
        }
        if (request.getInventory() != null) {
            predicates.add(cb.equal(root.get("inventory"), request.getInventory()));
        }
        if (request.getId() != null) {
            predicates.add(cb.equal(root.get("id"), request.getId()));
        }
        return predicates;
    }

}

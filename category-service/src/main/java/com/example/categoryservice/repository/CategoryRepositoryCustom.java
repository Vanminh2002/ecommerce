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
        // CriteriaBuilder là 1 công cụ để xây dựng các phần tử của 1 truy vấn dạng dộngd
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // Query để lấy danh sách
        //CriteriaQuery tạo 1 truy vấn sẽ trả về danh sách các đối tượng
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        //  Root<Category> tương ứng với bản category trong sql nó đại diện cho From category
        Root<Category> root = cq.from(Category.class);
        // tạo điều kiện lọc
        // tạo danh sách để chứa điều kiện lọc
        List<Predicate> predicates = buildPredicates(request, cb, root);

        // Áp dụng điều kiện và sắp xếp trước khi tạo query
        cq.where(predicates.toArray(new Predicate[0]));

        // Xử lý sắp xếp

        String sortBy = request.getSortBy() != null ? request.getSortBy() : "id";
        String sortDir = request.getSortDir() != null ? request.getSortDir() : "desc";


        Path<?> sortPath = root.get(sortBy);

        Order order = "desc".equalsIgnoreCase(sortDir) ? cb.desc(sortPath) : cb.asc(sortPath);


        cq.orderBy(order);

        // phân trang
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        TypedQuery<Category> query = entityManager.createQuery(cq);

        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Category> categories = query.getResultList();

        // đếm tổng số bản ghi
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Category> countRoot = countQuery.from(Category.class);
        List<Predicate> countPredicates = buildPredicates(request, cb, countRoot);
        countQuery.select(cb.count(countRoot))
                .where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();
//        return new PageImpl<>(categories, PageRequest.of(request.getPage(), request.getSize()), total);


        return new PageImpl<>(categories, PageRequest.of(page, size), total);

    }

    private static List<Predicate> buildPredicates(CategorySearchRequest request, CriteriaBuilder cb, Root<Category> root) {
        List<Predicate> predicates = new ArrayList<>();
        // truyền điều kiện lọc
        if (request.getCategoryName() != null && !request.getCategoryName().isEmpty()) {
            // lower không phân biện chữ hoa hay thường, dùng like để tìm chuỗi có chứa từ khóa
            predicates.add(cb.like(cb.lower(root.get("categoryName")),
                    "%" + request.getCategoryName().toLowerCase() + "%"));
        }
        if (request.getId() != null) {
            predicates.add(cb.equal(root.get("id"), request.getId()));
        }
        return predicates;
    }
}

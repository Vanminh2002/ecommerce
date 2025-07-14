package com.example.categoryservice.repository;

import com.example.categoryservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Boolean existsByCategoryName(String categoryName);
}

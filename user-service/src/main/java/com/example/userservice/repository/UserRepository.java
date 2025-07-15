package com.example.userservice.repository;

import com.example.userservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByName(String name);
    @Query(value = """
                SELECT * FROM users WHERE (:name is null or  name ILIKE '%' || :name|| '%')
            """,
            countQuery = """
                    SELECT  COUNT(*) FROM users 
                    WHERE   (:name IS NULL  OR  name ILIKE  '%' || :name|| '%')
                    """,
            nativeQuery = true)
    Page<User> searchByName(@Param("name") String name, Pageable pageable);
}

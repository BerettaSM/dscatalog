package com.devsuperior.dscatalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dscatalog.domain.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
                SELECT p.id
                FROM Product p
                WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%'))
                    AND (:ids IS NULL OR ELEMENT(p.categories).id IN :ids)
            """, countQuery = """
                SELECT COUNT(DISTINCT p.id)
                FROM Product p
                WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%'))
                    AND (:ids IS NULL OR ELEMENT(p.categories).id IN :ids)
            """)
    Page<Long> findAllProductIdsByNameAndCategoryIds(String name, @Param("ids") List<Long> ids, Pageable pageable);

    @Query(value = """
                FROM Product p
                JOIN FETCH p.categories
                WHERE (:productIds IS NULL OR p.id IN :productIds)
            """)
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds, Sort sort);

}

package com.devsuperior.dscatalog.tests;

import java.time.Instant;

import com.devsuperior.dscatalog.domain.dto.ProductDTO;
import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.domain.entities.Product;

public class Factory {
    
    public static Product createProduct() {
        Product product =  new Product(
            1L, "test product", "test", 
            1.0, "https://img.com/img.png", 
            Instant.parse("2020-10-20T03:00:00Z"));
        product.addCategories(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        return new ProductDTO(createProduct());
    }

    public static Category createCategory() {
        return new Category(1L, "Electronics");
    }

}

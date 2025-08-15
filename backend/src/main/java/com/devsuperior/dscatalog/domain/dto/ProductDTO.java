package com.devsuperior.dscatalog.domain.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.domain.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;

    private final Set<CategoryDTO> categories = new HashSet<>();

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        date = entity.getDate();
        addCategories(entity.getCategories().stream()
                .map(CategoryDTO::from)
                .toArray(CategoryDTO[]::new));
    }

    public final void addCategories(CategoryDTO... categories) {
        this.categories.addAll(Set.of(categories));
    }

    public Product toEntity() {
        Category[] categories = this.categories.stream()
                .map(CategoryDTO::toEntity)
                .toArray(Category[]::new);
        Product product = new Product(null, name, description, price, imgUrl, date);
        product.addCategories(categories);
        return product;
    }

    public static ProductDTO from(Product entity) {
        return new ProductDTO(entity);
    }

}

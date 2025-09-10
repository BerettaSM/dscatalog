package com.devsuperior.dscatalog.domain.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.domain.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @Size(min = 5, max = 60, message = "Name must have between 5 and 60 characters")
    @NotBlank(message = "Required field")
    private String name;

    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "Price must be positive")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "Product date must not be in the future")
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
        return new Product(null, name, description, price, imgUrl, date);
    }

    public static ProductDTO from(Product entity) {
        return new ProductDTO(entity);
    }

}

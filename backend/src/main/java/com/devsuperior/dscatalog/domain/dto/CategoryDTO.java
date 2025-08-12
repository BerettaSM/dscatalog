package com.devsuperior.dscatalog.domain.dto;

import com.devsuperior.dscatalog.domain.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public static CategoryDTO from(Category entity) {
        return new CategoryDTO(entity);
    }

    public Category toEntity() {
        return new Category(null, name);
    }

}

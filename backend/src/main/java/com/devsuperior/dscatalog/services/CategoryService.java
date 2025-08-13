package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.domain.dto.CategoryDTO;
import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryDTO::from)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO dto) {
        Category category = dto.toEntity();
        Category saved = categoryRepository.save(category);
        return CategoryDTO.from(saved);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        Category category = categoryRepository.getReferenceById(id);
        category.setName(dto.getName());
        Category updated = categoryRepository.save(category);
        return CategoryDTO.from(updated);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            categoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e, HttpStatus.CONFLICT);
        }
    }

}

package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.domain.dto.CategoryDTO;
import com.devsuperior.dscatalog.domain.dto.ProductDTO;
import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.domain.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDTO::from);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllByNameAndCategoryIds(
            String name,
            List<Long> categoryIds,
            Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Optional.of(pageable.getSort())
                        .filter(Sort::isSorted)
                        .orElse(Sort.by("name")));
        Page<Long> ids = productRepository.findAllProductIdsByNameAndCategoryIds(name, categoryIds, pageable);
        List<Product> products = productRepository.findProductsByIds(ids.getContent(), pageable.getSort());
        return new PageImpl<>(products, ids.getPageable(), ids.getTotalElements())
                .map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::from)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product product = new Product();
        copyDtoToEntity(dto, product);
        Product saved = productRepository.save(product);
        return ProductDTO.from(saved);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        Product product = productRepository.getReferenceById(id);
        copyDtoToEntity(dto, product);
        Product updated = productRepository.save(product);
        return ProductDTO.from(updated);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e, HttpStatus.CONFLICT);
        }
    }

    private final void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());
        entity.getCategories().clear();
        entity.addCategories(dto.getCategories().stream()
                .map(CategoryDTO::getId)
                .map(categoryRepository::getReferenceById)
                .toArray(Category[]::new));
    }

}

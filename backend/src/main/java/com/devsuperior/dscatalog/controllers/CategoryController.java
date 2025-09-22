package com.devsuperior.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.domain.dto.CategoryDTO;
import com.devsuperior.dscatalog.domain.entities.User;
import com.devsuperior.dscatalog.security.annotations.Authenticated;
import com.devsuperior.dscatalog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable, @AuthenticationPrincipal User user) {
        System.out.println("User: " + user);
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @Authenticated
    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO dto) {
        CategoryDTO saved = categoryService.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @Authenticated
    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long id,
            @RequestBody CategoryDTO dto) {
        CategoryDTO updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Authenticated
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

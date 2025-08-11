package com.devsuperior.dscatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = categoryService.findAll();
        return ResponseEntity.ok(list);
    }
    
}

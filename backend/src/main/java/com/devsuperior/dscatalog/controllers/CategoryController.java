package com.devsuperior.dscatalog.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.domain.entities.Category;

@RestController
@RequestMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = List.of(new Category(1L, "Books"), new Category(2L, "Electronics"));
        return ResponseEntity.ok(list);
    }

}

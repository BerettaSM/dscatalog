package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.devsuperior.dscatalog.domain.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private Product product;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        product = Factory.createProduct();
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        product.setId(null);
        long expectedId = productRepository.count() + 1;

        Product saved = productRepository.save(product);

        assertNotNull(saved.getId());
        assertEquals(expectedId, saved.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);
        Optional<?> optional = productRepository.findById(existingId);

        assertTrue(optional::isEmpty);
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<?> optional = productRepository.findById(existingId);

        assertTrue(optional::isPresent);
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
        Optional<?> optional = productRepository.findById(nonExistingId);

        assertTrue(optional::isEmpty);
    }

}

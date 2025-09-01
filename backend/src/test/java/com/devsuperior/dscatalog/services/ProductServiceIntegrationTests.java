package com.devsuperior.dscatalog.services;

import java.util.Comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import com.devsuperior.dscatalog.domain.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    public void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L; // per import.sql

    }

    @Test
    @DirtiesContext
    public void deleteShouldDeleteResourceWhenIdExists() {
        productService.deleteById(existingId);

        Assertions.assertEquals(
                countTotalProducts - 1,
                productRepository.count(),
                "Should contain the correct count of elements");
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteById(nonExistingId);
        }, "Should throw ResourceNotFoundException when id does not exist");
    }

    @Test
    public void findAllShouldReturnPageWhenPage0Size10() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.findAll(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findAllShouldReturnEmptyPageWhenPageDoesNotExist() {
        Pageable pageable = PageRequest.of(50, 10);

        Page<ProductDTO> result = productService.findAll(pageable);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortedByName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = productService.findAll(pageable);

        ProductDTO[] expected = result.stream().sorted(Comparator.comparing(ProductDTO::getName))
                .toArray(ProductDTO[]::new);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertArrayEquals(expected, result.stream().toArray(ProductDTO[]::new));
    }

}

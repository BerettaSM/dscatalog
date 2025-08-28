package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.devsuperior.dscatalog.domain.dto.ProductDTO;
import com.devsuperior.dscatalog.domain.entities.Category;
import com.devsuperior.dscatalog.domain.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(value = SpringExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private long existingId;
    private long nonExistingId;
    private long correlatedId;
    private Product product;
    private PageImpl<Product> page;
    private Category category;

    @BeforeEach
    public void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        correlatedId = 3L;

        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();

        Mockito.when(productRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(productRepository.existsById(existingId)).thenReturn(true);
        Mockito.when(productRepository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(productRepository.existsById(correlatedId)).thenReturn(true);
        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(product);
        Mockito.doThrow(EntityNotFoundException.class).when(productRepository).getReferenceById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(correlatedId);

        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.doThrow(EntityNotFoundException.class).when(categoryRepository).getReferenceById(nonExistingId);
    }

    @Test
    public void findByIdShouldNotThrowWhenIdExists() {
        assertDoesNotThrow(() -> {
            productService.findById(existingId);
        }, "Should not throw if id exists");
    }

        @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {
        ProductDTO result = productService.findById(existingId);
            assertNotNull(result, "Should return a non-null value if id exists");
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        }, "Should throw ResourceNotFoundException if id does not exist");
    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.findAll(pageable);

        assertNotNull(result);
        assertInstanceOf(Page.class, result);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO dto = Factory.createProductDTO();
        dto.setId(existingId);

        ProductDTO actual = productService.update(existingId, dto);

        assertNotNull(actual, "Should return a non-null value if id exists");
        assertEquals(dto.getId(), actual.getId(), "Id should be the same for input and output dto");
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        ProductDTO dto = Factory.createProductDTO();
        dto.setId(nonExistingId);

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.update(nonExistingId, dto);
        }, "Should throw ResourceNotFoundException when id does not exist");
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        assertDoesNotThrow(() -> {
            productService.deleteById(existingId);
        }, "ProductService.deleteById should not throw when id exists");

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdIsReferencedElsewhere() {
        assertThrows(DatabaseException.class, () -> {
            productService.deleteById(correlatedId);
        }, "ProductService.deleteById should throw when id is referenced on another table");
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteById(nonExistingId);
        }, "ProductService.deleteById should throw when id does not exist");
    }

}

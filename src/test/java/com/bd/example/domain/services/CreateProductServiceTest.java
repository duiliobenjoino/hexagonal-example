package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void shouldExecuteWithSuccess() {
        var product = new Product("Product Test");
        var productSaved = new Product(1, product.getName());

        when(repository.findBy(any()))
                .thenReturn(Collections.emptyList());
        when(repository.save(eq(product)))
                .thenReturn(productSaved);

        assertDoesNotThrow(() -> new CreateProductService(repository).execute(product));
        verify(repository, times(1)).findBy(product.getName());
        verify(repository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionWhenAlreadyProduct() {
        var product = new Product("Product Test");

        when(repository.findBy(any()))
                .thenReturn(List.of(product));

        assertThrows(RuntimeException.class,
                () -> new CreateProductService(repository).execute(product));
    }
}

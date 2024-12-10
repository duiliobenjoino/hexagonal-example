package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void shouldExecuteWithSuccess() {
        var product = new Product(1, "Product Test changed");
        var productSaved = new Product(1, "Product Test", ZonedDateTime.now(), 5L);

        when(repository.findById(eq(1)))
                .thenReturn(Optional.of(productSaved));
        when(repository.save(eq(product)))
                .thenReturn(product);

        assertDoesNotThrow(() -> new UpdateProductService(repository).execute(product));
        verify(repository, times(1)).findById(product.getId());
        verify(repository, times(1)).save(product);
        assertThat(product.getCreatedAT()).isEqualTo(productSaved.getCreatedAT());
        assertThat(product.getName()).isNotEqualTo(productSaved.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        var product = new Product(1, "Product Test changed");

        when(repository.findById(eq(1)))
                .thenReturn(Optional.empty());

        var errorMessage = assertThrows(
                RuntimeException.class,
                () -> new UpdateProductService(repository).execute(product)
        ).getMessage();

        assertThat(errorMessage).isEqualTo("Product %d not find".formatted(product.getId()));
    }
}

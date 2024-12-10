package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoveProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void shouldExecuteWithSuccess() {
        var productSaved = new Product(1, "Product Test", ZonedDateTime.now(), 5L);

        when(repository.findById(eq(1)))
                .thenReturn(Optional.of(productSaved));
        doNothing().when(repository)
                .remove(eq(1));

        assertDoesNotThrow(() -> new RemoveProductService(repository).execute(1));
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).remove(1);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(repository.findById(eq(1)))
                .thenReturn(Optional.empty());

        var errorMessage = assertThrows(
                RuntimeException.class,
                () -> new RemoveProductService(repository).execute(1)
        ).getMessage();

        assertThat(errorMessage).isEqualTo("Product %d not find".formatted(1));
    }
}

package com.bd.example.domain.services;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void shouldFindAllProducts() {
        var products = mockAllProducts();

        when(repository.findAll()).thenReturn(products);

        assertDoesNotThrow(() -> {
            var allProducts = new FindProductService(repository).findAll();
            assertThat(allProducts.size()).isEqualTo(products.size());
        });
    }

    @Test
    void shouldFindProductsByName() {
        var name = "Product Test";
        var allProducts = mockAllProducts();

        when(repository.findBy(eq(name)))
                .thenReturn(allProducts.stream()
                        .filter(product -> product.getName().equals(name))
                        .toList());

        assertDoesNotThrow(() -> {
            var result = new FindProductService(repository).findBy(name);
            assertThat(result.size()).isNotEqualTo(allProducts.size());
            assertThat(result.size()).isEqualTo(1);
        });
    }

    private List<Product> mockAllProducts() {
        return List.of(
                new Product(1, "Product Test", ZonedDateTime.now().minusDays(1), 5L),
                new Product(2, "Product Test 2", ZonedDateTime.now(), 10L)
        );
    }
}

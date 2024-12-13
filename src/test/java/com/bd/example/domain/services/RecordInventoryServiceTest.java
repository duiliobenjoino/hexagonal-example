package com.bd.example.domain.services;

import com.bd.example.domain.InventoryHistory;
import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.InventoryHistoryRepository;
import com.bd.example.domain.ports.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecordInventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryHistoryRepository inventoryHistoryRepository;

    @Test
    void shouldExecuteWithSuccess() {
        final var quantity = 5L;
        final var productId = 1;
        final var productSaved = new Product(productId, "Product Test", ZonedDateTime.now(), 2L);

        final var expectedCurrentInventory = 7L;
        final var now = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());

        Mockito.mockStatic(ZonedDateTime.class);
        when(ZonedDateTime.now()).thenReturn(now);

        final var productChanged = new Product(productId, productSaved.getName(), productSaved.getCreatedAT(), expectedCurrentInventory);
        final var inventoryHistory = new InventoryHistory(productChanged, quantity);
        final var inventoryHistorySaved = new InventoryHistory(
                1L, inventoryHistory.getCreatedAt(), productId, productSaved.getName(),
                inventoryHistory.getQuantity(), inventoryHistory.getCurrentInventory());

        when(productRepository.findById(eq(productId)))
                .thenReturn(Optional.of(productSaved));
        when(productRepository.save(any()))
                .thenReturn(productSaved);
        when(inventoryHistoryRepository.save(any()))
                .thenReturn(inventoryHistorySaved);

        assertDoesNotThrow(() -> {
            final var result = new RecordInventoryService(inventoryHistoryRepository, productRepository).execute(productId, quantity);
            verify(productRepository, times(1)).findById(productId);
            verify(productRepository, times(1)).save(productChanged);
            verify(inventoryHistoryRepository, times(1)).save(inventoryHistory);
            assertThat(result.getCurrentInventory()).isEqualTo(expectedCurrentInventory);
        });
    }
}

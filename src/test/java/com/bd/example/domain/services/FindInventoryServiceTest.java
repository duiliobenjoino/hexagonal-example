package com.bd.example.domain.services;

import com.bd.example.domain.InventoryHistory;
import com.bd.example.domain.ports.repositories.InventoryHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindInventoryServiceTest {

    @Mock
    private InventoryHistoryRepository repository;

    @Test
    void shouldFindProductIdAndCreatedFrom() {
        final var productId = 1;
        final var dateFrom = LocalDate.of(2024, 12, 13);
        final var inventoryHistories = mockInventory(productId, dateFrom);

        when(repository.findBy(eq(productId), any()))
                .thenReturn(inventoryHistories);

        assertDoesNotThrow(() -> {
            final var result = new FindInventoryService(repository).findBy(productId, dateFrom);
            assertThat(result.size()).isEqualTo(inventoryHistories.size());
        });
    }

    @Test
    void shouldToDefineCreatedFromWhenIsNull() {
        final var createdFrom = new FindInventoryService(repository).getCreatedFrom(null);
        assertThat(createdFrom).isNotNull();
        assertThat(createdFrom).isEqualTo(ZonedDateTime.of(LocalDate.of(0, 1, 1), LocalTime.MIN, ZoneId.systemDefault()));
    }

    private List<InventoryHistory> mockInventory(final Integer productId, final LocalDate dateFrom) {
        return List.of(
                new InventoryHistory(1L, ZonedDateTime.of(dateFrom, LocalTime.MIN, ZoneId.systemDefault()), productId, "Product Teste", 1L, 1L),
                new InventoryHistory(2L, ZonedDateTime.of(dateFrom.plusDays(1), LocalTime.MIN, ZoneId.systemDefault()), productId, "Product Teste", 4L, 5L)
        );
    }
}

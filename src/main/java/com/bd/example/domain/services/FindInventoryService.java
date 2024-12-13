package com.bd.example.domain.services;

import com.bd.example.domain.InventoryHistory;
import com.bd.example.domain.ports.repositories.InventoryHistoryRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.Objects.isNull;

public class FindInventoryService {
    private final InventoryHistoryRepository inventoryHistoryRepository;

    public FindInventoryService(
            final InventoryHistoryRepository inventoryHistoryRepository) {
        this.inventoryHistoryRepository = inventoryHistoryRepository;
    }

    public List<InventoryHistory> findBy(final Integer productId, final LocalDate dateFrom) {
        return inventoryHistoryRepository.findBy(productId, getCreatedFrom(dateFrom));
    }

    ZonedDateTime getCreatedFrom(final LocalDate dateFrom) {
        return isNull(dateFrom)
                ? ZonedDateTime.of(LocalDate.of(0, 1, 1), LocalTime.MIN, ZoneId.systemDefault())
                : ZonedDateTime.of(dateFrom, LocalTime.MIN, ZoneId.systemDefault());
    }

}

package com.bd.example.domain.ports.repositories;

import com.bd.example.domain.InventoryHistory;

public interface InventoryHistoryRepository {
    InventoryHistory save(InventoryHistory history);

//    List<InventoryHistory> findBy(Integer productId, ZonedDateTime createdFrom);
}

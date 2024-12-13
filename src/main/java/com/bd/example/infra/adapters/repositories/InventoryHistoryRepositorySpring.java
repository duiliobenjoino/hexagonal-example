package com.bd.example.infra.adapters.repositories;

import com.bd.example.infra.adapters.entities.InventoryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface InventoryHistoryRepositorySpring extends JpaRepository<InventoryHistoryEntity, Integer> {
    List<InventoryHistoryEntity> findByProductIdAndCreatedAtGreaterThanEqual(Integer productId, ZonedDateTime createdAt);

}

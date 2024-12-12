package com.bd.example.infra.adapters.repositories;

import com.bd.example.infra.adapters.entities.InventoryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
public interface InventoryHistoryRepositorySpring extends JpaRepository<InventoryHistoryEntity, Integer> {
}

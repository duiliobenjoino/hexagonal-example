package com.bd.example.infra.adapters.repositories;

import com.bd.example.domain.InventoryHistory;
import com.bd.example.domain.ports.repositories.InventoryHistoryRepository;
import com.bd.example.infra.adapters.entities.InventoryHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryHistoryRepositoryImp implements InventoryHistoryRepository {

    private final InventoryHistoryRepositorySpring repository;

    @Override
    public InventoryHistory save(final InventoryHistory inventoryHistory) {
        final var entity = repository.save(new InventoryHistoryEntity(inventoryHistory));
        return entity.toDomain();
    }

    @Override
    public List<InventoryHistory> findBy(final Integer productId, final ZonedDateTime createdFrom) {
        return repository.findByProductIdAndCreatedAtGreaterThanEqual(productId, createdFrom)
                .stream().map(InventoryHistoryEntity::toDomain)
                .toList();
    }

}

package com.bd.example.infra.adapters.repositories;

import com.bd.example.infra.adapters.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
public interface ProductRepositorySpring extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByNameIgnoreCase(String name);
}

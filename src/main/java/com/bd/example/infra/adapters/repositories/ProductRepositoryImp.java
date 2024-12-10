package com.bd.example.infra.adapters.repositories;

import com.bd.example.domain.Product;
import com.bd.example.domain.ports.repositories.ProductRepository;
import com.bd.example.infra.adapters.entities.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImp implements ProductRepository {

    private final ProductRepositorySpring repository;

    @Override
    public Product save(final Product product) {
        final var entity = repository.save(new ProductEntity(product));
        return entity.toDomain();
    }

    @Override
    public void remove(final Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findBy(final String name) {
        return repository.findByNameIgnoreCase(name)
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findById(final Integer id) {
        return repository.findById(id).map(ProductEntity::toDomain);
    }
}

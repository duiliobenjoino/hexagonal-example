package com.bd.example.infra.adapters.entities;

import com.bd.example.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Setter
    @Column(name = "created_at")
    private ZonedDateTime createdAT;
    @Column(name = "current_inventory")
    private Long currentInventory;

    public ProductEntity(final Product domain) {
        this.id = domain.getId();
        this.name = domain.getName();
        this.createdAT = domain.getCreatedAT();
        this.currentInventory = domain.getCurrentInventory();
    }

    public Product toDomain() {
        return new Product(id, name, createdAT, currentInventory);
    }
}

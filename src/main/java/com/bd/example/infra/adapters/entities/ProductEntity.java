package com.bd.example.infra.adapters.entities;

import com.bd.example.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;

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
    @Version
    private int version;

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

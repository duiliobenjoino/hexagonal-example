package com.bd.example.aplication.adapters.dtos;

import com.bd.example.domain.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDTO {
    private Integer id;
    @NotBlank(message = "Field name is required")
    private String name;
    private ZonedDateTime createdAt;
    private Long currentInventory;

    public Product toDomain() {
        return new Product(this.name);
    }

    public Product toDomain(final Integer id) {
        return new Product(id, this.name);
    }

    public ProductDTO(String name) {
        this.name = name;
    }

    public static ProductDTO fromDomain(final Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getCreatedAT(), product.getCurrentInventory());
    }
}

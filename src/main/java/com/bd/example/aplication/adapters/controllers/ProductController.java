package com.bd.example.aplication.adapters.controllers;

import com.bd.example.aplication.adapters.dtos.InventoryRequestDTO;
import com.bd.example.aplication.adapters.dtos.InventoryResponseDTO;
import com.bd.example.aplication.adapters.dtos.ProductDTO;
import com.bd.example.domain.services.CreateProductService;
import com.bd.example.domain.services.FindProductService;
import com.bd.example.domain.services.RecordInventoryMovement;
import com.bd.example.domain.services.RemoveProductService;
import com.bd.example.domain.services.UpdateProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {

    private final CreateProductService createService;
    private final UpdateProductService updateService;
    private final RemoveProductService removeService;
    private final FindProductService findService;
    private final RecordInventoryMovement inventoryService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> find(
            @RequestParam(required = false) final String name) {
        final var dto = this.findService.findBy(name)
                .stream().map(ProductDTO::fromDomain)
                .toList();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable final Integer id) {
        final var dto = ProductDTO.fromDomain(this.findService.findById(id));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid final ProductDTO dto) {
        final var product = this.createService.execute(dto.toDomain());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductDTO.fromDomain(product));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @RequestBody @Valid final ProductDTO dto, @PathVariable final Integer id) {
        final var product = this.updateService.execute(dto.toDomain(id));
        return ResponseEntity
                .ok()
                .body(ProductDTO.fromDomain(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable final Integer id) {
        this.removeService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/inventory")
    public ResponseEntity<InventoryResponseDTO> inventory(
            @RequestBody @Valid final InventoryRequestDTO dto,
            @PathVariable final Integer id) {
        final var inventoryHistory = this.inventoryService.execute(id, dto.getQuantity());
        return ResponseEntity
                .ok()
                .body(InventoryResponseDTO.fromDomain(inventoryHistory));
    }
}

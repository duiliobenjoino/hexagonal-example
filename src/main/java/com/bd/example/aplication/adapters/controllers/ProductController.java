package com.bd.example.aplication.adapters.controllers;

import com.bd.example.aplication.adapters.dtos.ProductDTO;
import com.bd.example.domain.services.CreateProductService;
import com.bd.example.domain.services.FindProductService;
import com.bd.example.domain.services.RemoveProductService;
import com.bd.example.domain.services.UpdateProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {

    private final CreateProductService createService;
    private final UpdateProductService updateService;
    private final RemoveProductService removeService;
    private final FindProductService findService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> find(@RequestParam(required = false) String name) {
        var dto = this.findService.findBy(name)
                .stream().map(ProductDTO::fromDomain)
                .toList();
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
        var dto = ProductDTO.fromDomain(this.findService.findById(id));
        return ResponseEntity.ok().body(dto);
    }
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO dto) {
        var product = this.createService.execute(dto.toDomain());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductDTO.fromDomain(product));
    }
    @PostMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO dto, @PathVariable Integer id) {
        var product = this.updateService.execute(dto.toDomain(id));
        return ResponseEntity
                .ok()
                .body(ProductDTO.fromDomain(product));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable Integer id) {
        this.removeService.execute(id);
        return ResponseEntity.noContent().build();
    }
}

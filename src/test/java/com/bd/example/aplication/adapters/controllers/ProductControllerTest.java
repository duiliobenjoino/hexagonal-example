package com.bd.example.aplication.adapters.controllers;

import com.bd.example.aplication.adapters.dtos.ProductDTO;
import com.bd.example.domain.Product;
import com.bd.example.domain.services.FindProductService;
import com.bd.example.infra.adapters.entities.ProductEntity;
import com.bd.example.infra.adapters.repositories.ProductRepositorySpring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepositorySpring repository;

    @Autowired
    private FindProductService findService;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/products";
        repository.deleteAll();
    }

    @Test
    void shouldCreateProduct() {
        var product = new ProductDTO("Test Product");

        ResponseEntity<ProductDTO> response = restTemplate.postForEntity(baseUrl, product, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Product");
    }

    @Test
    void shouldUpdateProduct() {
        var productSaved = repository.save(new ProductEntity(new Product("Test Product")));
        var product = new ProductDTO("Test Product changed");

        ResponseEntity<ProductDTO> response = restTemplate.postForEntity(baseUrl + "/" + productSaved.getId(), product, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getId()).isEqualTo(productSaved.getId());
        assertThat(requireNonNull(response.getBody()).getCreatedAt()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getCurrentInventory()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Product changed");
    }

    @Test
    void shouldGetAllProducts() {
        repository.saveAll(
                List.of(
                        new ProductEntity(new Product("Product 1")),
                        new ProductEntity(new Product("Product 2"))
                )
        );

        ResponseEntity<ProductEntity[]> response = restTemplate.getForEntity(baseUrl, ProductEntity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).length)
                .isEqualTo(2);
    }

    @Test
    void shouldProductsByName() {
        repository.saveAll(
                List.of(
                        new ProductEntity(new Product("Product 1")),
                        new ProductEntity(new Product("Product 2"))
                )
        );

        ResponseEntity<ProductEntity[]> response = restTemplate.getForEntity(baseUrl + "?name=Product 1", ProductEntity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requireNonNull(response.getBody()).length)
                .isEqualTo(1);
        assertTrue(Arrays.stream(requireNonNull(response.getBody()))
                .anyMatch(p -> p.getName().equals("Product 1")));
    }

    @Test
    void shouldRemoveProduct() {
        var products = List.of(
                new ProductEntity(new Product("Product 1")),
                new ProductEntity(new Product("Product 2"))
        );
        repository.saveAll(products);

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + products.getFirst().getId(),
                HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var allProducts = this.findService.findAll();

        assertThat(allProducts.size()).isEqualTo(1);
        assertThat(allProducts.stream().noneMatch(p -> p.getName().equals("Product 1")))
                .isTrue();
    }
}

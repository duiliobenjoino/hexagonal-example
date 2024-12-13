package com.bd.example.aplication.adapters.controllers;

import com.bd.example.aplication.adapters.dtos.InventoryRequestDTO;
import com.bd.example.aplication.adapters.dtos.InventoryResponseDTO;
import com.bd.example.aplication.adapters.dtos.ProductDTO;
import com.bd.example.infra.adapters.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource dataSource;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/products";
        final var jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DELETE FROM product");
        jdbcTemplate.execute("DELETE FROM inventory_history");
    }

    @Test
    void shouldCreateProduct() {
        final var product = new ProductDTO("Test New Product");

        final ResponseEntity<ProductDTO> response = restTemplate.postForEntity(baseUrl, product, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(product.getName());
    }

    @Test
    @Rollback(false)
    void shouldUpdateProduct() {
        final var productSaved = insertProduct(new ProductDTO("Test Product")).getBody();
        final var product = new ProductDTO("Test Product changed");

        final ResponseEntity<ProductDTO> response = restTemplate.postForEntity(
                baseUrl + "/" + requireNonNull(productSaved).getId(),
                product, ProductDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getId()).isEqualTo(productSaved.getId());
        assertThat(requireNonNull(response.getBody()).getCreatedAt()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getCurrentInventory()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Test Product changed");
    }

    @Test
    void shouldGetAllProducts() {
        insertProduct(new ProductDTO("Product 1"));
        insertProduct(new ProductDTO("Product 2"));

        final ResponseEntity<ProductEntity[]> response = restTemplate.getForEntity(baseUrl, ProductEntity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).length)
                .isEqualTo(2);
    }

    @Test
    void shouldProductsByName() {
        insertProducts(new ProductDTO("Product 1"), new ProductDTO("Product 2"));

        final ResponseEntity<ProductEntity[]> response = restTemplate.getForEntity(baseUrl + "?name=Product 1", ProductEntity[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requireNonNull(response.getBody()).length)
                .isEqualTo(1);
        assertTrue(Arrays.stream(requireNonNull(response.getBody()))
                .anyMatch(p -> p.getName().equals("Product 1")));
    }

    @Test
    void shouldRemoveProduct() {
        final var product = insertProduct(new ProductDTO("Product 1")).getBody();
        insertProducts(new ProductDTO("Product 2"), new ProductDTO("Product 3"));


        final ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + requireNonNull(product).getId(),
                HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        final var allProducts = Arrays.stream(
                requireNonNull(findAllProducts().getBody())
        ).toList();

        assertThat(allProducts.size()).isEqualTo(2);
        assertThat(allProducts.stream().noneMatch(p -> p.getName().equals("Product 1")))
                .isTrue();
    }

    @Test
    void shouldRecordMovement() {
        final var request = new InventoryRequestDTO(2L);
        final var product = insertProduct(new ProductDTO("Test Product")).getBody();

        final ResponseEntity<InventoryResponseDTO> response = restTemplate.postForEntity(
                baseUrl + "/" + requireNonNull(product).getId() + "/inventory/record",
                request, InventoryResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(requireNonNull(response.getBody()).getId()).isNotNull();
        assertThat(response.getBody().getProductName()).isEqualTo("Test Product");
    }

    @Test
    void shouldFindInventoryByProductId() {
        final var productId = requireNonNull(insertProduct(new ProductDTO("Product 1")).getBody()).getId();
        insertRecordMovement(productId, 5L);
        insertRecordMovement(productId, 15L);

        final ResponseEntity<InventoryResponseDTO[]> response = restTemplate.getForEntity(
                baseUrl + "/" + productId + "/inventory", InventoryResponseDTO[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(requireNonNull(response.getBody()).length)
                .isEqualTo(2);
        assertTrue(Arrays.stream(requireNonNull(response.getBody()))
                .anyMatch(p -> p.getCurrentInventory().equals(20L)));
    }

    private ResponseEntity<ProductDTO> insertProduct(final ProductDTO productDTO) {
        return restTemplate.postForEntity(baseUrl, productDTO, ProductDTO.class);
    }

    private void insertProducts(final ProductDTO... products) {
        for (final ProductDTO productDTO : products) {
            insertProduct(productDTO);
        }
    }

    private ResponseEntity<ProductEntity[]> findAllProducts() {
        return restTemplate.getForEntity(baseUrl, ProductEntity[].class);
    }

    private void insertRecordMovement(final Integer productId, final Long value) {
        final var request = new InventoryRequestDTO(value);
        restTemplate.postForEntity(
                baseUrl + "/" + productId + "/inventory/record",
                request, InventoryResponseDTO.class);
    }

}

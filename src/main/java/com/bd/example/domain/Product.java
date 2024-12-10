package com.bd.example.domain;

import java.time.ZonedDateTime;

public class Product {
    private Integer id;
    private String name;
    private ZonedDateTime createdAT;
    private Long currentInventory;

    public Product() {}

    public Product(final Integer id, final String name, final ZonedDateTime createdAT, final Long currentInventory) {
        this.id = id;
        this.name = name;
        this.createdAT = createdAT;
        this.currentInventory = currentInventory;
    }

    public Product(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Product(final String name) {
        this.name = name;
        this.createdAT = ZonedDateTime.now();
        this.currentInventory = 0L;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreatedAT() {
        return createdAT;
    }

    public Long getCurrentInventory() {
        return currentInventory;
    }

    public void setCreatedAT(final ZonedDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public void setCurrentInventory(final Long currentInventory) {
        this.currentInventory = currentInventory;
    }
}

package com.fudn.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * TODO-1: @Document cho MongoDB collection "products"
 * Fields: id (String), name, price, stock
 */
@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;       // e.g. "P1", "P2"

    private String name;

    private BigDecimal price;

    private int stock;
}

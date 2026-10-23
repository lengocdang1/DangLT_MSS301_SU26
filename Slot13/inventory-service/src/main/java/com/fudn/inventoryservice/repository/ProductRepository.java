package com.fudn.inventoryservice.repository;

import com.fudn.inventoryservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}

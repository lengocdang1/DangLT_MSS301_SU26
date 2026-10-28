package com.fudn.inventoryservice.config;

import com.fudn.inventoryservice.model.Product;
import com.fudn.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                        new Product("P1", "Laptop", 1000, 10),
                        new Product("P2", "Mouse", 20, 5)));
            }
        };
    }
}

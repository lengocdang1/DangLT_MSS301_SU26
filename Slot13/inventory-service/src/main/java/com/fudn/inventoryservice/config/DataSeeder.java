package com.fudn.inventoryservice.config;

import com.fudn.inventoryservice.model.Product;
import com.fudn.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * TODO-1: Nạp dữ liệu mẫu P1, P2 khi collection rỗng.
 * MongoDB không hỗ trợ data.sql → dùng CommandLineRunner.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        // Chỉ seed khi collection products rỗng (idempotent)
        if (productRepository.count() == 0) {
            Product p1 = new Product("P1", "Laptop Dell XPS 15", new BigDecimal("25000000"), 50);
            Product p2 = new Product("P2", "Mouse Logitech MX Master", new BigDecimal("1500000"), 100);
            productRepository.saveAll(List.of(p1, p2));
            log.info("Seeded 2 products: P1, P2");
        } else {
            log.info("Products collection already has data – skipping seed.");
        }
    }
}

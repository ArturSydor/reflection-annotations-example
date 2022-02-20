package com.learning.basic.dependency_injection_container.demo;

import com.learning.basic.dependency_injection_container.annotation.Autowired;
import com.learning.basic.dependency_injection_container.annotation.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

@Component
public class ProductRepository {

    @Autowired
    private DummyDS dataSource;

    public List<Product> getAllProducts() {
        return dataSource.getRows(getDummyProducts());
    }

    public Supplier<List<Product>> getDummyProducts() {
        return () -> List.of(
                new Product(1L, BigDecimal.valueOf(10.4), "Milk"),
                new Product(2L, BigDecimal.valueOf(4), "Coffee"),
                new Product(3L, BigDecimal.valueOf(12.5), "Tea"),
                new Product(4L, BigDecimal.valueOf(8.7), "Bread"),
                new Product(5L, BigDecimal.valueOf(0.9), "Apple")
        );
    }

}

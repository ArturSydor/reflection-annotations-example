package com.learning.basic.dependency_injection_container.demo;

import java.math.BigDecimal;

public record Product(
        Long id,
        BigDecimal price,
        String description
) {
}

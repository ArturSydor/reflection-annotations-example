package com.learning.basic.dependency_injection_container.demo;

import com.learning.basic.dependency_injection_container.annotation.Autowired;
import com.learning.basic.dependency_injection_container.annotation.Component;

@Component
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public void displayAvailableProducts() {
        repository.getAllProducts().forEach(System.out::println);
    }

}

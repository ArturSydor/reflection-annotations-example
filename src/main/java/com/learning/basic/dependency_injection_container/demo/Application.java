package com.learning.basic.dependency_injection_container.demo;

import com.learning.basic.dependency_injection_container.context.ApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(ApplicationConfig.class);
        ProductService service = applicationContext.getBean(ProductService.class);
        service.displayAvailableProducts();
    }
}

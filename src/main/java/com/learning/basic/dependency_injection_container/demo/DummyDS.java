package com.learning.basic.dependency_injection_container.demo;

import com.learning.basic.dependency_injection_container.annotation.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class DummyDS {

    public <T> List<T> getRows(Supplier<List<T>> db) {
        return db.get();
    }

}

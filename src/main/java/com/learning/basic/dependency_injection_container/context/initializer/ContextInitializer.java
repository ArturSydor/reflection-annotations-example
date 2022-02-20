package com.learning.basic.dependency_injection_container.context.initializer;

import java.util.Map;

public interface ContextInitializer {

    public Map<Class<?>, Object> initializeContext(Class<?>... configClasses);

}

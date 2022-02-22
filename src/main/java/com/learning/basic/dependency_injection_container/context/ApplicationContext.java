package com.learning.basic.dependency_injection_container.context;

import com.learning.basic.dependency_injection_container.context.initializer.SimpleContextInitializer;
import com.learning.basic.dependency_injection_container.context.utilities.FieldUtility;

import java.util.Map;

public class ApplicationContext {

    private final Map<Class<?>, Object> context;

    public ApplicationContext(Class<?>... configClasses) {
        context = SimpleContextInitializer.initializeContext(configClasses);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        return (T) FieldUtility.injectFields(context.get(type), type, this);
    }

}

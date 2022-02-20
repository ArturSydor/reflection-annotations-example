package com.learning.basic.dependency_injection_container.context.utilities;

import com.learning.basic.dependency_injection_container.exception.ContextInitializationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtilities {

    public Object createObjectFromClass(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new ContextInitializationException("Failed to initialize context", e);
        }
    }

    public Class<?> getClassObjectFromName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new ContextInitializationException("Failed to initialize context", e);
        }
    }


}

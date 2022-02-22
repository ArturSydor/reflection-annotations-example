package com.learning.basic.dependency_injection_container.context.initializer;

import com.learning.basic.dependency_injection_container.annotation.Component;
import com.learning.basic.dependency_injection_container.annotation.ComponentScan;
import com.learning.basic.dependency_injection_container.annotation.Configuration;
import com.learning.basic.dependency_injection_container.context.utilities.FileUtilities;
import com.learning.basic.dependency_injection_container.context.utilities.ReflectionUtilities;
import com.learning.basic.dependency_injection_container.exception.ContextInitializationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class SimpleContextInitializer {

    public static Map<Class<?>, Object> initializeContext(Class<?>... configClasses) {
        for (Class<?> configClass : configClasses) {
            if (configClass.isAnnotationPresent(Configuration.class)) {
                var packageToScan = configClass.getAnnotation(ComponentScan.class).value();
                var packageStructure = "target/classes/" + packageToScan.replace(".", "/");

                return Arrays.stream(FileUtilities.findFiles(packageStructure))
                        .map(file -> FileUtilities.mapFileNameToClassName(file.getName(), ".class", packageToScan))
                        .map(ReflectionUtilities::getClassObjectFromName)
                        .filter(clazz -> clazz.isAnnotationPresent(Component.class))
                        .collect(toUnmodifiableMap(Function.identity(), ReflectionUtilities::createObjectFromClass));

            } else {
                throw new ContextInitializationException(String.format("""
                        Class %s not marked with @Configuration annotation
                        cannot be used for application context creation
                        """, configClass.getName()));
            }
        }

        return Collections.emptyMap();
    }

}

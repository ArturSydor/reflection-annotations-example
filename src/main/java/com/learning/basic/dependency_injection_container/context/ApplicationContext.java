package com.learning.basic.dependency_injection_container.context;

import com.learning.basic.dependency_injection_container.annotation.Autowired;
import com.learning.basic.dependency_injection_container.annotation.Component;
import com.learning.basic.dependency_injection_container.annotation.ComponentScan;
import com.learning.basic.dependency_injection_container.annotation.Configuration;
import com.learning.basic.dependency_injection_container.exception.BeanInitializationException;
import com.learning.basic.dependency_injection_container.exception.ContextInitializationException;
import com.learning.basic.dependency_injection_container.exception.PackageNotExistsException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class ApplicationContext {

    private final Map<Class<?>, Object> context;

    public ApplicationContext(Class<?>... configClasses) {
        context = initializeContext(configClasses);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        return (T) injectFields(context.get(type), type);
    }

    private Object injectFields(Object object, Class<?> type) {
        Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> assignFieldValue(field, object));

        return object;
    }

    private void assignFieldValue(Field field, Object object) {
        try {
            field.setAccessible(Boolean.TRUE);
            Object fieldValue = context.get(field.getType());
            field.set(object, fieldValue);
            injectFields(fieldValue, field.getType());
        } catch (IllegalAccessException e) {
            throw new BeanInitializationException("Failed to initialize bean of type " + object.getClass(), e);
        }
    }

    private Map<Class<?>, Object> initializeContext(Class<?>... configClasses) {
        for (Class<?> configClass : configClasses) {
            if (configClass.isAnnotationPresent(Configuration.class)) {
                var packageToScan = configClass.getAnnotation(ComponentScan.class).value();
                var packageStructure = "target/classes/" + packageToScan.replace(".", "/");

                return Arrays.stream(findFiles(packageStructure))
                        .map(file -> mapFileNameToClassName(file.getName(), ".class", packageToScan))
                        .map(this::getClassObjectFromName)
                        .filter(clazz -> clazz.isAnnotationPresent(Component.class))
                        .collect(toUnmodifiableMap(Function.identity(), this::createObjectFromClass));

            } else {
                throw new ContextInitializationException(String.format("""
                        Class %s not marked with @Configuration annotation
                        cannot be used for application context creation
                        """, configClass.getName()));
            }
        }

        return Collections.emptyMap();
    }

    private Object createObjectFromClass(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new ContextInitializationException("Failed to initialize context", e);
        }
    }

    private String mapFileNameToClassName(String fileName, String fileSuffix, String classPackage) {
        return classPackage.isBlank() ? fileName.replace(fileSuffix, "") :
                classPackage + "." + fileName.replace(fileSuffix, "");
    }

    private Class<?> getClassObjectFromName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new ContextInitializationException("Failed to initialize context", e);
        }
    }

    private File[] findFiles(String packageStructure) {
        return getFileOrThrowException(packageStructure)
                .listFiles(f -> f.getName().endsWith(".class"));
    }

    private File getFileOrThrowException(String fileName) {
        var file = new File(fileName);
        if (!file.exists()) {
            throw new PackageNotExistsException(String.format("File %s doesn't exists", fileName));
        }
        return file;
    }

}

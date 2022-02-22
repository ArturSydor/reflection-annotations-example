package com.learning.basic.dependency_injection_container.context.utilities;

import com.learning.basic.dependency_injection_container.annotation.Autowired;
import com.learning.basic.dependency_injection_container.context.ApplicationContext;
import com.learning.basic.dependency_injection_container.exception.BeanInitializationException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Arrays;

@UtilityClass
public class FieldUtility {

    public Object injectFields(Object object, Class<?> type, ApplicationContext context) {
        Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Autowired.class))
                .forEach(field -> assignFieldValue(field, object, context));

        return object;
    }

    public void assignFieldValue(Field field, Object object, ApplicationContext context) {
        try {
            field.setAccessible(Boolean.TRUE);
            Object fieldValue = context.getBean(field.getType());
            field.set(object, fieldValue);
            injectFields(fieldValue, field.getType(), context);
        } catch (IllegalAccessException e) {
            throw new BeanInitializationException("Failed to initialize bean of type " + object.getClass(), e);
        }
    }


}

package com.learning.basic.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadingAnnotations {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var utilClass = Class.forName("com.learning.basic.annotation.Utility");
        var constructor = utilClass.getConstructor();
        var util = (Utility) constructor.newInstance();

        if(utilClass.isAnnotationPresent(MostUsed.class)) {
            var typeAnnotation = utilClass.getAnnotation(MostUsed.class);
            System.out.println("Utility class is marked with @MostUsed annotation");
            util.doSth(typeAnnotation.value());
        }

        var methods = utilClass.getDeclaredMethods();
        for (Method method: methods) {
            if(method.isAnnotationPresent(MostUsed.class)) {
                MostUsed annotation = method.getAnnotation(MostUsed.class);
                String value = annotation.value();
                method.invoke(util, value);
            }
        }
    }

}

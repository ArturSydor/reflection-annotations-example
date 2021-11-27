package com.learning.basic.reflaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodsOfTheClass {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // non-declared: return all public elements of that class and its superclass
        // declared: return all elements of that class only

        var iron = new IronMan(20, "Jack");
        System.out.println(iron);
        var ironClazz = iron.getClass();

        var publicMethods = ironClazz.getMethods();
        for (Method method : publicMethods) {
            System.out.println(method.getName());
        }
        System.out.println("----------------");
        var allMethods = ironClazz.getDeclaredMethods();
        for (Method method : allMethods) {
            System.out.println(method.getName());
        }

        var powerMethod = ironClazz.getMethod("setPower", int.class);
        powerMethod.invoke(iron, 500);
        System.out.println(iron);

        var nameMethod = ironClazz.getDeclaredMethod("setName", String.class);
        nameMethod.setAccessible(Boolean.TRUE);
        nameMethod.invoke(iron, "Dummy");
        System.out.println(iron);
    }
}
